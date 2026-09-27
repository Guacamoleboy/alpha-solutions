package alpha.domain.eventcourtreservation.controller;

import alpha.crud.CRUDController;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventcourtreservation.mapper.response.EventCourtReservationResponseMapper;
import alpha.domain.eventcourtreservation.dto.request.EventCourtReservationRequestDTO;
import alpha.domain.eventcourtreservation.service.EventCourtReservationService;
import alpha.exception.ApiException;
import alpha.security.jwt.JwtService;
import alpha.service.EntityManagerService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class EventCourtReservationController extends CRUDController<EventCourtReservation> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventCourtReservationController(EntityManagerService<EventCourtReservation> service) {
        super(service, EventCourtReservation.class, EventCourtReservationResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            if (!"OWNER".equals(JwtService.getClaimRole(token))) {
                throw new ApiException(403, "Only owners can reserve courts for events");
            }
            EventCourtReservationRequestDTO dto = ctx.bodyAsClass(EventCourtReservationRequestDTO.class);
            EventCourtReservation created = ((EventCourtReservationService) classService).createEventCourtReservation(dto);
            return EventCourtReservationResponseMapper.toDTO(created);
        }, "Event court reservation created");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void deleteById(Context ctx) {
        String token = ContextHelper.extractBearerToken(ctx);
        if (!"OWNER".equals(JwtService.getClaimRole(token))) {
            throw new ApiException(403, "Only owners can remove event court reservations");
        }
        super.deleteById(ctx);
    }

}