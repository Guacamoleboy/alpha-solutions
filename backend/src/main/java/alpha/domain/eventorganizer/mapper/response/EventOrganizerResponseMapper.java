package alpha.domain.eventorganizer.mapper.response;

import alpha.domain.eventorganizer.dto.response.EventOrganizerResponseDTO;
import alpha.domain.eventorganizer.entity.EventOrganizer;

public class EventOrganizerResponseMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static EventOrganizerResponseDTO toDTO(EventOrganizer entity) {
        EventOrganizerResponseDTO dto = new EventOrganizerResponseDTO();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        if (entity.getEventRequest() != null) dto.setEventRequestId(entity.getEventRequest().getId());
        if (entity.getMember() != null) dto.setMemberId(entity.getMember().getId());
        return dto;
    }

}