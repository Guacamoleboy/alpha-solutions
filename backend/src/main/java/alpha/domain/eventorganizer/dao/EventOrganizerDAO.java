package alpha.domain.eventorganizer.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import jakarta.persistence.EntityManager;

public class EventOrganizerDAO extends EntityManagerDAO<EventOrganizer> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventOrganizerDAO(EntityManager em) {
        super(em, EventOrganizer.class);
    }

}