package alpha.domain.booking.service;

import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.booking.dto.request.BookingRequestDTO;
import alpha.domain.booking.entity.Booking;
import alpha.domain.court.entity.Court;
import alpha.domain.court.service.CourtService;
import alpha.domain.member.entity.Member;
import alpha.domain.member.service.MemberService;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class BookingService extends EntityManagerService<Booking> {

    // Attributes
    private final BookingDAO bookingDAO;
    private final CourtService courtService;
    private final MemberService memberService;
    private final OperatingHourService operatingHourService;

    // _________________________________________________________________________________________________________________

    public BookingService(EntityManager em, CourtService courtService, MemberService memberService, OperatingHourService operatingHourService) {
        super(new BookingDAO(em), Booking.class);
        this.bookingDAO = (BookingDAO) this.entityManagerDAO;
        this.courtService = courtService;
        this.memberService = memberService;
        this.operatingHourService = operatingHourService;
    }

    // _________________________________________________________________________________________________________________

    public Booking createBooking(Integer memberId, BookingRequestDTO dto) {
        validateBookingTime(dto.getStartTime(), dto.getEndTime());
        Member member = validateMember(memberId);

        // Validate daily
        validateMemberDailyBooking(member, dto.getStartTime());

        Court court = validateCourt(dto.getCourtId());
        validateMembership(member, court);

        validateOperatingHours(
                dto.getStartTime(),
                dto.getEndTime()
        );

        validateBookingAvailability(
                dto.getCourtId(),
                dto.getStartTime(),
                dto.getEndTime()
        );

        Booking booking = Booking.builder()
                .member(member)
                .court(court)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return create(booking);
    }

    // _________________________________________________________________________________________________________________
    // TODO: Implement premium + super premium to allow multiple bookings

    private void validateMemberDailyBooking(Member member, LocalDateTime startTime) {
        if (member.getMembership() != null && member.getMembership().getId() >= 4) {
            return;
        }

        Integer memberId = member.getId();
        if (bookingDAO.existsBookingForMemberOnDate(memberId, startTime)) {
            throw new ApiException(
                    409, "Du har allerede én booking på denne dag. Fjern venligst en."
            );
        }
    }

    // _________________________________________________________________________________________________________________

    private void validateBookingTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new ApiException(
                    400, "Start time and end time are required"
            );
        }
        if (!startTime.isBefore(endTime)) {
            throw new ApiException(
                    400, "Start time must be before end time"
            );
        }
        if (!startTime.toLocalDate().equals(endTime.toLocalDate())) {
            throw new ApiException(
                    400, "Booking must start and end on the same day"
            );
        }
    }

    // _________________________________________________________________________________________________________________

    private Member validateMember(Integer memberId) {
        Member member = memberService.getById(memberId);
        if (member == null) {
            throw new ApiException(
                    404, "Member not found"
            );
        }
        return member;
    }

    // _________________________________________________________________________________________________________________

    private Court validateCourt(Integer courtId) {
        Court court = courtService.getById(courtId);
        if (court == null) {
            throw new ApiException(
                    404, "Banen findes ikke"
            );
        }
        if (!Boolean.TRUE.equals(court.getActive())) {
            throw new ApiException(
                    400, "Banen er lukket lige pt"
            );
        }
        return court;
    }

    // _________________________________________________________________________________________________________________

    private void validateMembership(Member member, Court court) {
        if (court.getRequiredMembership() == null) {
            return;
        }
        if (member.getMembership() == null
                || member.getMembership().getId() < court.getRequiredMembership().getId()) {
            throw new ApiException(
                    403, "Du har ikke adgang til denne funktion. Opgradér venligst medlemsskab"
            );
        }
    }

    // _________________________________________________________________________________________________________________

    private void validateOperatingHours(LocalDateTime startTime, LocalDateTime endTime) {
        OperatingHour operatingHour = operatingHourService.getByDayOfWeek(startTime.getDayOfWeek());
        if (operatingHour == null) {
            throw new ApiException(
                    500, "Operating hours not configured"
            );
        }
        if (Boolean.TRUE.equals(operatingHour.getClosed())) {
            throw new ApiException(
                    400, "Court is closed on this day"
            );
        }
        if (startTime.toLocalTime().isBefore(operatingHour.getOpenTime()) || endTime.toLocalTime().isAfter(operatingHour.getCloseTime())) {
            throw new ApiException(
                    400, "Booking is outside operating hours"
            );
        }
    }

    // _________________________________________________________________________________________________________________

    private void validateBookingAvailability(Integer courtId, LocalDateTime startTime, LocalDateTime endTime) {
        if (bookingDAO.existsOverlappingBooking(courtId, startTime, endTime)) {
            throw new ApiException(
                    409, "Du har allerede en booking på denne dag og tidspunkt."
            );
        }
    }

    // _________________________________________________________________________________________________________________

    public List<Booking> getMemberBookings(Integer memberId) {
        validateMember(memberId);
        return getAll().stream()
                .filter(booking -> booking.getMember().getId().equals(memberId))
                .sorted(Comparator.comparing(Booking::getStartTime).reversed())
                .toList();
    }

}