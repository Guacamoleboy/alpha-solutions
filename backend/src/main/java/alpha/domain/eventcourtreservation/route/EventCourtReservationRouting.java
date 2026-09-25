package alpha.domain.eventcourtreservation.route;

import alpha.crud.CRUDRouting;
import alpha.domain.eventcourtreservation.controller.EventCourtReservationController;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventcourtreservation.service.EventCourtReservationService;
import jakarta.persistence.EntityManagerFactory;

public class EventCourtReservationRouting extends CRUDRouting<EventCourtReservation> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventCourtReservationRouting(EntityManagerFactory emf) {
        super("/event-court-reservations", new EventCourtReservationController(new EventCourtReservationService(emf.createEntityManager())));
    }

}