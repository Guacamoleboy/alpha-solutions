package alpha.domain.eventrequest.mapper.response;

import alpha.domain.eventrequest.dto.response.EventRequestResponseDTO;
import alpha.domain.eventrequest.entity.EventRequest;

public class EventRequestResponseMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static EventRequestResponseDTO toDTO(EventRequest entity) {
        EventRequestResponseDTO dto = new EventRequestResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setGuestCount(entity.getGuestCount());
        dto.setRequestedCourtCount(entity.getRequestedCourtCount());
        dto.setEquipmentRequired(entity.getEquipmentRequired());
        dto.setEventCode(entity.getEventCode());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        if (entity.getRequester() != null) dto.setRequesterId(entity.getRequester().getId());
        return dto;
    }

}