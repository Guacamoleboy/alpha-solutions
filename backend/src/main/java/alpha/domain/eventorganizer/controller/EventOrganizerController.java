package alpha.domain.eventorganizer.controller;

import alpha.crud.CRUDController;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventorganizer.mapper.response.EventOrganizerResponseMapper;
import alpha.domain.eventorganizer.dto.request.EventOrganizerRequestDTO;
import alpha.domain.eventorganizer.service.EventOrganizerService;
import alpha.service.EntityManagerService;
import alpha.security.jwt.JwtService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import alpha.exception.ApiException;
import io.javalin.http.Context;

public class EventOrganizerController extends CRUDController<EventOrganizer> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventOrganizerController(EntityManagerService<EventOrganizer> service) {
        super(service, EventOrganizer.class, EventOrganizerResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            if (!"OWNER".equals(JwtService.getClaimRole(token))) {
                throw new ApiException(403, "Only owners can add event co-organizers");
            }
            EventOrganizerRequestDTO dto = ctx.bodyAsClass(EventOrganizerRequestDTO.class);
            EventOrganizer created = ((EventOrganizerService) classService).createEventOrganizer(dto);
            return EventOrganizerResponseMapper.toDTO(created);
        }, "Event co-organizer added");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void deleteById(Context ctx) {
        String token = ContextHelper.extractBearerToken(ctx);
        if (!"OWNER".equals(JwtService.getClaimRole(token))) {
            throw new ApiException(403, "Only owners can remove event co-organizers");
        }
        super.deleteById(ctx);
    }

}