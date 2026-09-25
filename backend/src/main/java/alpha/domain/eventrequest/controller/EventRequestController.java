package alpha.domain.eventrequest.controller;

import alpha.crud.CRUDController;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.dto.request.EventRequestRequestDTO;
import alpha.domain.eventrequest.mapper.response.EventRequestResponseMapper;
import alpha.domain.eventrequest.service.EventRequestService;
import alpha.security.jwt.JwtService;
import alpha.service.EntityManagerService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class EventRequestController extends CRUDController<EventRequest> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventRequestController(EntityManagerService<EventRequest> service) {
        super(service, EventRequest.class, EventRequestResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            Integer memberId = JwtService.getClaimMemberId(token);
            EventRequestRequestDTO dto = ctx.bodyAsClass(EventRequestRequestDTO.class);
            EventRequest created = ((EventRequestService) classService).createEventRequest(memberId, dto);
            return EventRequestResponseMapper.toDTO(created);
        }, "Event request created");
    }

    // _________________________________________________________________________________________________________________

    public void getMine(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            Integer memberId = JwtService.getClaimMemberId(token);
            return ((EventRequestService) classService).getMemberEventRequests(memberId)
                    .stream()
                    .map(EventRequestResponseMapper::toDTO)
                    .toList();
        }, "Member event requests retrieved");
    }
    
}