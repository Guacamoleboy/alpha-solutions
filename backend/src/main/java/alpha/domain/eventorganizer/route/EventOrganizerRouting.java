package alpha.domain.eventorganizer.route;

import alpha.crud.CRUDRouting;
import alpha.domain.eventorganizer.controller.EventOrganizerController;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventorganizer.service.EventOrganizerService;
import jakarta.persistence.EntityManagerFactory;

public class EventOrganizerRouting extends CRUDRouting<EventOrganizer> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventOrganizerRouting(EntityManagerFactory emf) {
        super("/event-organizers", new EventOrganizerController(new EventOrganizerService(emf.createEntityManager())));
    }

}