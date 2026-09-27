package alpha.domain.eventrequest;

import alpha.domain.court.dao.CourtDAO;
import alpha.domain.court.entity.Court;
import alpha.domain.eventcourtreservation.dao.EventCourtReservationDAO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventrequest.dao.EventRequestDAO;
import alpha.domain.eventrequest.dto.request.EventRequestRequestDTO;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.membership.dao.MembershipDAO;
import alpha.domain.membership.entity.Membership;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.domain.settings.operatinghour.dao.OperatingHourDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public final class EventTestData {

    // Attributes
    private final EntityManager em;

    // _________________________________________________________________________________________________________________

    public EventTestData(EntityManager em) {
        this.em = em;
    }

    // _________________________________________________________________________________________________________________

    public Member createMember(RoleName roleName, boolean premium) {
        Role role = new RoleDAO(em).getByName(roleName);
        if (role == null) {
            role = new RoleDAO(em).create(Role.builder().name(roleName).build());
        }

        Membership membership = premium ? createPremiumMembership() : null;
        String uniqueId = UUID.randomUUID().toString();
        return new MemberDAO(em).create(Member.builder()
                .firstName("Event")
                .lastName("Test")
                .email("event.test." + uniqueId + "@example.com")
                .passwordHashed("hashedPassword")
                .role(role)
                .membership(membership)
                .build());
    }

    // _________________________________________________________________________________________________________________

    public Court createCourt() {
        return new CourtDAO(em).create(Court.builder()
                .name("Event Test Court " + UUID.randomUUID())
                .active(true)
                .build());
    }

    // _________________________________________________________________________________________________________________

    public EventRequest createEventRequest(Member requester, EventRequestStatus status, LocalDateTime start, LocalDateTime end) {
        return new EventRequestDAO(em).create(EventRequest.builder()
                .name("Test Event")
                .startTime(start)
                .endTime(end)
                .guestCount(4)
                .requestedCourtCount(1)
                .equipmentRequired(false)
                .organizerEmail(requester.getEmail())
                .status(status)
                .requester(requester)
                .build());
    }

    // _________________________________________________________________________________________________________________

    public EventCourtReservation createReservation(EventRequest eventRequest, Court court) {
        return new EventCourtReservationDAO(em).create(EventCourtReservation.builder()
                .eventRequest(eventRequest)
                .court(court)
                .build());
    }

    // _________________________________________________________________________________________________________________

    public LocalDateTime futureStart() {
        return LocalDate.now().plusDays(14).atTime(10, 0);
    }

    // _________________________________________________________________________________________________________________

    public void configureOperatingHours(LocalDate date) {
        OperatingHourDAO operatingHourDAO = new OperatingHourDAO(em);
        OperatingHour operatingHour = operatingHourDAO.getAll().stream()
                .filter(hour -> hour.getDayOfWeek() == date.getDayOfWeek())
                .findFirst()
                .orElse(null);
        if (operatingHour == null) {
            operatingHour = OperatingHour.builder()
                    .dayOfWeek(date.getDayOfWeek())
                    .build();
        }
        operatingHour.setOpenTime(LocalTime.of(8, 0));
        operatingHour.setCloseTime(LocalTime.of(22, 0));
        operatingHour.setClosed(false);
        if (operatingHour.getId() == null) {
            operatingHourDAO.create(operatingHour);
        } else {
            operatingHourDAO.update(operatingHour);
        }
    }

    // _________________________________________________________________________________________________________________

    public EventRequestRequestDTO validEventRequest(String name, LocalDateTime start, LocalDateTime end) {
        EventRequestRequestDTO dto = new EventRequestRequestDTO();
        dto.setName(name);
        dto.setStartTime(start);
        dto.setEndTime(end);
        dto.setGuestCount(4);
        dto.setRequestedCourtCount(1);
        dto.setEquipmentRequired(false);
        return dto;
    }

    // _________________________________________________________________________________________________________________

    private Membership createPremiumMembership() {
        MembershipDAO membershipDAO = new MembershipDAO(em);
        Membership existingPremium = membershipDAO.getAll().stream()
                .filter(membership -> membership.getId() >= 3)
                .findFirst()
                .orElse(null);
        if (existingPremium != null) {
            return existingPremium;
        }

        Membership membership = null;
        while (membership == null || membership.getId() < 3) {
            membership = membershipDAO.create(Membership.builder()
                    .name("Event Test Membership " + UUID.randomUUID())
                    .active(true)
                    .build());
        }
        return membership;
    }

}