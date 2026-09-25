package alpha.domain.eventcourtreservation.service;

import alpha.domain.eventcourtreservation.dao.EventCourtReservationDAO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class EventCourtReservationService extends EntityManagerService<EventCourtReservation> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventCourtReservationService(EntityManager em) {
        super(new EventCourtReservationDAO(em), EventCourtReservation.class);
    }

}