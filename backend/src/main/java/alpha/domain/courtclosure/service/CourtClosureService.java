package alpha.domain.courtclosure.service;

import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.court.entity.Court;
import alpha.domain.courtclosure.dao.CourtClosureDAO;
import alpha.domain.courtclosure.dto.request.CourtClosureRequestDTO;
import alpha.domain.courtclosure.entity.CourtClosure;
import alpha.domain.courtclosure.mapper.request.CourtClosureRequestMapper;
import alpha.domain.eventcourtreservation.dao.EventCourtReservationDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class CourtClosureService extends EntityManagerService<CourtClosure> {

    // Attributes
    private final EntityManager em;
    private final CourtClosureDAO courtClosureDAO;
    private final BookingDAO bookingDAO;
    private final EventCourtReservationDAO eventCourtReservationDAO;
    private final OperatingHourService operatingHourService;

    // _________________________________________________________________________________________________________________

    public CourtClosureService(EntityManager em) {
        super(new CourtClosureDAO(em), CourtClosure.class);
        this.em = em;
        this.courtClosureDAO = (CourtClosureDAO) this.entityManagerDAO;
        this.bookingDAO = new BookingDAO(em);
        this.eventCourtReservationDAO = new EventCourtReservationDAO(em);
        this.operatingHourService = new OperatingHourService(em);
    }

    // _________________________________________________________________________________________________________________

    public CourtClosure createCourtClosure(CourtClosureRequestDTO dto) {
        if (dto == null || dto.getCourtId() == null || dto.getStartTime() == null || dto.getEndTime() == null || dto.getReason() == null) {
            throw new ApiException(400, "Court, start time, end time and reason are required");
        }

        LocalDateTime startTime = dto.getStartTime();
        LocalDateTime endTime = dto.getEndTime();
        if (!startTime.isBefore(endTime) || !startTime.toLocalDate().equals(endTime.toLocalDate())) {
            throw new ApiException(400, "Closure must have a valid time period on one day");
        }

        Court court = em.find(Court.class, dto.getCourtId());
        if (court == null) {
            throw new ApiException(404, "Court not found");
        }
        if (!Boolean.TRUE.equals(court.getActive())) {
            throw new ApiException(400, "Inactive courts cannot be closed for a specific time");
        }

        OperatingHour operatingHour = operatingHourService.getByDayOfWeek(startTime.getDayOfWeek());
        if (operatingHour == null) {
            throw new ApiException(500, "Operating hours not configured");
        }
        if (Boolean.TRUE.equals(operatingHour.getClosed())) {
            throw new ApiException(400, "Facility is closed on this day");
        }
        if (operatingHour.getOpenTime() == null || operatingHour.getCloseTime() == null
                || startTime.toLocalTime().isBefore(operatingHour.getOpenTime())
                || endTime.toLocalTime().isAfter(operatingHour.getCloseTime())) {
            throw new ApiException(400, "Closure must be within operating hours");
        }

        if (bookingDAO.existsOverlappingBooking(court.getId(), startTime, endTime)
                || eventCourtReservationDAO.existsAcceptedReservationOverlap(court.getId(), startTime, endTime)) {
            throw new ApiException(409, "Court already has a booking or event reservation during this time");
        }
        if (courtClosureDAO.existsOverlappingClosure(court.getId(), startTime, endTime)) {
            throw new ApiException(409, "Court already has a closure during this time");
        }

        CourtClosure closure = CourtClosureRequestMapper.toEntity(dto);
        closure.setCourt(court);
        return create(closure);
    }

}