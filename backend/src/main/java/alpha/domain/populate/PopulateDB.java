package alpha.domain.populate;

import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.service.MembershipService;
import alpha.domain.booking.dto.request.BookingRequestDTO;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.service.BookingService;
import alpha.domain.court.entity.Court;
import alpha.domain.court.enums.CourtSurface;
import alpha.domain.court.service.CourtService;
import alpha.domain.member.entity.Member;
import alpha.domain.member.service.MemberService;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import alpha.exception.ApiException;
import alpha.util.BCryptHash;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.util.Collections.rotate;

public class PopulateDB {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static String populateMembership(EntityManager em) {

        // Instantiate
        MembershipService membershipService = new MembershipService(em);

        if (!membershipService.getAll().isEmpty()) {
            return "Already added";
        }

        // Clean DB
        // Memberships
        Membership free = Membership.builder()
                .name("Free")
                .price(BigDecimal.valueOf(0))
                .currency("DKK")
                .active(true)
                .build();
        Membership basic = Membership.builder()
                .name("Basic")
                .price(BigDecimal.valueOf(99))
                .currency("DKK")
                .active(true)
                .build();
        Membership premium = Membership.builder()
                .name("Premium")
                .price(BigDecimal.valueOf(199))
                .currency("DKK")
                .active(true)
                .build();
        Membership superPremium = Membership.builder()
                .name("Super Premium")
                .price(BigDecimal.valueOf(299))
                .currency("DKK")
                .active(true)
                .build();

        // Persist
        membershipService.create(free);
        membershipService.create(basic);
        membershipService.create(premium);
        membershipService.create(superPremium);

        return "Memberships added";

    }

    // _________________________________________________________________________________________________________________

    public static String restart(EntityManager em) {
        boolean startedTransaction = false;
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
                startedTransaction = true;
            }

            String tables = (String) em.createNativeQuery(
                    "SELECT string_agg(format('%I.%I', schemaname, tablename), ', ') " +
                            "FROM pg_tables WHERE schemaname = 'public'"
            ).getSingleResult();

            if (tables != null && !tables.isBlank()) {
                em.createNativeQuery("TRUNCATE TABLE " + tables + " RESTART IDENTITY CASCADE")
                        .executeUpdate();
            }

            if (startedTransaction) {
                em.getTransaction().commit();
            }
            return "Database restarted";
        } catch (RuntimeException exception) {
            if (startedTransaction && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw exception;
        }
    }

    // _________________________________________________________________________________________________________________

    public static String populateCourts(EntityManager em) {
        CourtService courtService = new CourtService(em);
        MembershipService membershipService = new MembershipService(em);
        List<Court> existingCourts = courtService.getAll();

        if (!existingCourts.isEmpty()) {
            return "Already added";
        }

        Membership premium = membershipService.findEntityByColumn("Premium", Membership.Fields.NAME);
        if (premium == null) {
            throw new ApiException(400, "Populate memberships before populating courts");
        }

        for (int number = 1; number <= 8; number++) {
            createCourt(courtService, "Court " + number, true, null);
        }
        for (int number = 9; number <= 11; number++) {
            createCourt(courtService, "Court " + number, false, null);
        }
        for (int number = 1; number <= 7; number++) {
            createCourt(courtService, "Premium Court " + number, true, premium);
        }

        return "Courts added";
    }

    // _________________________________________________________________________________________________________________

    private static void createCourt(CourtService courtService, String name, boolean active, Membership requiredMembership) {
        courtService.create(Court.builder()
                .name(name)
                .active(active)
                .surface(randomCourtSurface())
                .requiredMembership(requiredMembership)
                .build());
    }

    // _________________________________________________________________________________________________________________

    private static CourtSurface randomCourtSurface() {
        CourtSurface[] surfaces = CourtSurface.values();
        return surfaces[ThreadLocalRandom.current().nextInt(surfaces.length)];
    }

    // _________________________________________________________________________________________________________________

    public static void populateMembers(EntityManager em) {

        MembershipService membershipService = new MembershipService(em);
        MemberService memberService = new MemberService(em);
        Membership free = membershipService.findEntityByColumn("Free", Membership.Fields.NAME);

        // Membership validation
        if (free == null) {
            throw new ApiException(400, "Populate memberships before populating members");
        }

        // Owner check
        if (!memberService.existByColumn("owner@owner.dk", Member.Fields.EMAIL)) {
            Member owner = Member.builder()
                    .firstName("Owner")
                    .lastName("Owner")
                    .email("owner@owner.dk")
                    .passwordHashed("Password123456!")
                    .membership(free)
                    .build();
            memberService.createOwnerMember(owner);
        }

        if (memberService.existByColumn("test1@test.dk", Member.Fields.EMAIL)) {
            return;
        }

        // Member Spawner
        String passwordHash = BCryptHash.hash("Password123456!");
        for (int index = 1; index <= 1000; index++) {
            Member member = Member.builder()
                    .firstName("Test")
                    .lastName("Member " + index)
                    .email("test" + index + "@test.dk")
                    .passwordHashed(passwordHash)
                    .membership(free)
                    .build();
            memberService.createMemberWithPasswordHash(member);

        }

    }

    // _________________________________________________________________________________________________________________

    public static void populateBookings(EntityManager em) {

        MembershipService membershipService = new MembershipService(em);
        MemberService memberService = new MemberService(em);
        CourtService courtService = new CourtService(em);
        OperatingHourService operatingHourService = new OperatingHourService(em);
        BookingService bookingService = new BookingService(em, courtService, memberService, operatingHourService);

        List<Court> courts = courtService.getAll().stream()
                .filter(court -> Boolean.TRUE.equals(court.getActive()))
                .sorted(Comparator.comparing(Court::getId))
                .toList();
        List<Member> members = memberService.getAll().stream()
                .filter(member -> !"owner@owner.dk".equals(member.getEmail()))
                .sorted(Comparator.comparing(Member::getId))
                .toList();
        List<OperatingHour> operatingHours = operatingHourService.getAll();
        Membership free = membershipService.findEntityByColumn("Free", Membership.Fields.NAME);
        Membership superPremium = membershipService.findEntityByColumn("Super Premium", Membership.Fields.NAME);

        if (courts.isEmpty()) {
            throw new ApiException(400, "Populate courts before populating bookings");
        }
        if (members.isEmpty() || free == null || superPremium == null) {
            throw new ApiException(400, "Populate members and memberships before populating bookings");
        }
        if (operatingHours.isEmpty()) {
            throw new ApiException(400, "Populate operating hours before populating bookings");
        }

        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        LocalDate firstDay = previousMonth.atDay(1);
        LocalDate lastDay = previousMonth.atEndOfMonth();

        promotePremiumTestMembers(members, superPremium, memberService);

        boolean alreadyPopulated = bookingService.getAll().stream()
                .anyMatch(booking -> !booking.getStartTime().toLocalDate().isBefore(firstDay)
                        && !booking.getStartTime().toLocalDate().isAfter(lastDay));
        if (alreadyPopulated) {
            return;
        }

        Map<DayOfWeek, OperatingHour> hoursByDay = new HashMap<>();
        for (OperatingHour operatingHour : operatingHours) {
            hoursByDay.put(operatingHour.getDayOfWeek(), operatingHour);
        }

        int dayIndex = 0;
        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            OperatingHour operatingHour = hoursByDay.get(date.getDayOfWeek());
            if (operatingHour == null || Boolean.TRUE.equals(operatingHour.getClosed())) {
                continue;
            }

            List<LocalDateTime> startTimes = hourlyStartTimes(date, operatingHour);
            if (startTimes.isEmpty()) {
                continue;
            }

            int targetCourtCount = dailyTargetCourtCount(courts.size(), dayIndex);
            List<Court> dailyCourts = rotatedCourts(courts, dayIndex).subList(0, targetCourtCount);
            Set<Integer> freeMembersUsedToday = new HashSet<>();
            int memberIndex = dayIndex % members.size();

            for (LocalDateTime startTime : startTimes) {
                for (Court court : dailyCourts) {
                    Member member = findEligibleMember(
                            members,
                            free,
                            court,
                            freeMembersUsedToday,
                            memberIndex
                    );
                    if (member == null) {
                        continue;
                    }

                    BookingRequestDTO request = new BookingRequestDTO();
                    request.setCourtId(court.getId());
                    request.setStartTime(startTime);
                    request.setEndTime(startTime.plusHours(1));
                    bookingService.createBooking(member.getId(), request);

                    if (member.getMembership() == null
                            || member.getMembership().getId() < superPremium.getId()) {
                        freeMembersUsedToday.add(member.getId());
                    }
                    memberIndex = (members.indexOf(member) + 1) % members.size();
                }
            }
            dayIndex++;
        }
    }

    // _________________________________________________________________________________________________________________

    private static void promotePremiumTestMembers(List<Member> members, Membership superPremium, MemberService memberService) {
        int promoted = 0;
        for (Member member : members) {
            if (promoted >= 100) {
                break;
            }
            if (member.getMembership() == null || member.getMembership().getId() < superPremium.getId()) {
                member.setMembership(superPremium);
                memberService.update(member);
                promoted++;
            }
        }
    }

    // _________________________________________________________________________________________________________________

    private static List<LocalDateTime> hourlyStartTimes(LocalDate date, OperatingHour operatingHour) {
        List<LocalDateTime> startTimes = new ArrayList<>();
        if (operatingHour.getOpenTime() == null || operatingHour.getCloseTime() == null) {
            return startTimes;
        }

        LocalDateTime closeTime = LocalDateTime.of(date, operatingHour.getCloseTime());
        for (LocalDateTime startTime = LocalDateTime.of(date, operatingHour.getOpenTime());
             !startTime.plusHours(1).isAfter(closeTime);
             startTime = startTime.plusHours(1)) {
            startTimes.add(startTime);
        }
        return startTimes;
    }

    // _________________________________________________________________________________________________________________

    private static int dailyTargetCourtCount(int courtCount, int dayIndex) {
        double occupancy = dayIndex % 3 == 1 ? 0.7 : 0.4;
        return max(1, (int) ceil(courtCount * occupancy));
    }

    // _________________________________________________________________________________________________________________

    private static List<Court> rotatedCourts(List<Court> courts, int offset) {
        List<Court> rotated = new ArrayList<>(courts);
        rotate(rotated, -(offset % rotated.size()));
        return rotated;
    }

    // _________________________________________________________________________________________________________________

    private static Member findEligibleMember(List<Member> members, Membership free, Court court, Set<Integer> freeMembersUsedToday, int startIndex) {
        for (int offset = 0; offset < members.size(); offset++) {
            Member member = members.get((startIndex + offset) % members.size());
            if (court.getRequiredMembership() != null
                    && (member.getMembership() == null
                    || member.getMembership().getId() < court.getRequiredMembership().getId())) {
                continue;
            }
            boolean premium = member.getMembership() != null
                    && member.getMembership().getId() >= 4;
            if (!premium && freeMembersUsedToday.contains(member.getId())) {
                continue;
            }
            return member;
        }
        return null;
    }

}