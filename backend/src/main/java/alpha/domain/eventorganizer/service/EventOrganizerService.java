package alpha.domain.eventorganizer.service;

import alpha.domain.eventorganizer.dao.EventOrganizerDAO;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class EventOrganizerService extends EntityManagerService<EventOrganizer> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventOrganizerService(EntityManager em) {
        super(new EventOrganizerDAO(em), EventOrganizer.class);
    }
    
}