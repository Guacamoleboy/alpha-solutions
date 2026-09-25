package alpha.domain.eventcourtreservation.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import jakarta.persistence.EntityManager;

public class EventCourtReservationDAO extends EntityManagerDAO<EventCourtReservation> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventCourtReservationDAO(EntityManager em) {
        super(em, EventCourtReservation.class);
    }
    
}