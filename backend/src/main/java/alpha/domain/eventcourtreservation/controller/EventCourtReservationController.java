package alpha.domain.eventcourtreservation.controller;

import alpha.crud.CRUDController;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventcourtreservation.mapper.response.EventCourtReservationResponseMapper;
import alpha.service.EntityManagerService;

public class EventCourtReservationController extends CRUDController<EventCourtReservation> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventCourtReservationController(EntityManagerService<EventCourtReservation> service) {
        super(service, EventCourtReservation.class, EventCourtReservationResponseMapper::toDTO);
    }

}