package alpha.domain.eventrequest.mapper.request;

import alpha.domain.eventrequest.dto.request.EventRequestRequestDTO;
import alpha.domain.eventrequest.entity.EventRequest;

public class EventRequestRequestMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static EventRequest toEntity(EventRequestRequestDTO dto) {
        EventRequest entity = new EventRequest();
        entity.setName(dto.getName());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setGuestCount(dto.getGuestCount());
        entity.setRequestedCourtCount(dto.getRequestedCourtCount());
        entity.setEquipmentRequired(dto.getEquipmentRequired());
        entity.setEventCode(dto.getEventCode());
        return entity;
    }

}