package alpha.domain.eventorganizer.controller;

import alpha.crud.CRUDController;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventorganizer.mapper.response.EventOrganizerResponseMapper;
import alpha.service.EntityManagerService;

public class EventOrganizerController extends CRUDController<EventOrganizer> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventOrganizerController(EntityManagerService<EventOrganizer> service) {
        super(service, EventOrganizer.class, EventOrganizerResponseMapper::toDTO);
    }

}