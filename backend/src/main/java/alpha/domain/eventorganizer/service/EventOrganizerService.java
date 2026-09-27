package alpha.domain.eventorganizer.service;

import alpha.domain.eventorganizer.dao.EventOrganizerDAO;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventorganizer.dto.request.EventOrganizerRequestDTO;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.member.entity.Member;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class EventOrganizerService extends EntityManagerService<EventOrganizer> {

    // Attributes
    private final EntityManager em;

    // _________________________________________________________________________________________________________________

    public EventOrganizerService(EntityManager em) {
        super(new EventOrganizerDAO(em), EventOrganizer.class);
        this.em = em;
    }

    // _________________________________________________________________________________________________________________

    public EventOrganizer createEventOrganizer(EventOrganizerRequestDTO dto) {
        if (dto == null || dto.getEventRequestId() == null || dto.getMemberId() == null) {
            throw new ApiException(400, "Event and co-organizer are required");
        }
        EventRequest eventRequest = em.find(EventRequest.class, dto.getEventRequestId());
        Member member = em.find(Member.class, dto.getMemberId());
        if (eventRequest == null || member == null) {
            throw new ApiException(404, "Event or member not found");
        }
        if (eventRequest.getRequester().getId().equals(member.getId())) {
            throw new ApiException(400, "The main organizer cannot also be a co-organizer");
        }
        var eventOrganizers = getAll().stream()
                .filter((organizer) -> organizer.getEventRequest().getId().equals(eventRequest.getId()))
                .toList();
        if (eventOrganizers.size() >= 3) {
            throw new ApiException(400, "An event can have at most three co-organizers");
        }
        boolean alreadyAdded = eventOrganizers.stream()
                .anyMatch((organizer) -> organizer.getMember().getId().equals(member.getId()));
        if (alreadyAdded) {
            throw new ApiException(409, "This member is already a co-organizer");
        }
        return create(EventOrganizer.builder().eventRequest(eventRequest).member(member).build());
    }
    
}