package alpha.domain.eventcourtreservation.service;

import alpha.domain.eventcourtreservation.dao.EventCourtReservationDAO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventcourtreservation.dto.request.EventCourtReservationRequestDTO;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.court.entity.Court;
import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.courtclosure.dao.CourtClosureDAO;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class EventCourtReservationService extends EntityManagerService<EventCourtReservation> {

    // Attributes
    private final EntityManager em;
    private final EventCourtReservationDAO reservationDAO;
    private final BookingDAO bookingDAO;
    private final CourtClosureDAO courtClosureDAO;

    // _________________________________________________________________________________________________________________

    public EventCourtReservationService(EntityManager em) {
        super(new EventCourtReservationDAO(em), EventCourtReservation.class);
        this.em = em;
        this.reservationDAO = (EventCourtReservationDAO) this.entityManagerDAO;
        this.bookingDAO = new BookingDAO(em);
        this.courtClosureDAO = new CourtClosureDAO(em);
    }

    // _________________________________________________________________________________________________________________

    public EventCourtReservation createEventCourtReservation(EventCourtReservationRequestDTO dto) {
        if (dto == null || dto.getEventRequestId() == null || dto.getCourtId() == null) {
            throw new ApiException(400, "Event and court are required");
        }
        EventRequest eventRequest = em.find(EventRequest.class, dto.getEventRequestId());
        Court court = em.find(Court.class, dto.getCourtId());
        if (eventRequest == null || court == null) {
            throw new ApiException(404, "Event or court not found");
        }
        if (eventRequest.getStatus() != EventRequestStatus.ACCEPTED) {
            throw new ApiException(400, "Only confirmed events can reserve courts");
        }
        if (reservationDAO.existsOtherAcceptedEventOverlap(court.getId(), eventRequest.getId(), eventRequest.getStartTime(), eventRequest.getEndTime())) {
            throw new ApiException(409, "This court is already reserved for another event during that time");
        }
        if (bookingDAO.existsOverlappingBooking(court.getId(), eventRequest.getStartTime(), eventRequest.getEndTime())) {
            throw new ApiException(409, "This court already has a member booking during that time");
        }
        if (courtClosureDAO.existsOverlappingClosure(court.getId(), eventRequest.getStartTime(), eventRequest.getEndTime())) {
            throw new ApiException(409, "This court is closed during that time");
        }
        return create(EventCourtReservation.builder().eventRequest(eventRequest).court(court).build());
    }

}