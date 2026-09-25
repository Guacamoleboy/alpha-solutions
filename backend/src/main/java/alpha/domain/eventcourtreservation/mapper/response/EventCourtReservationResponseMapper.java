package alpha.domain.eventcourtreservation.mapper.response;

import alpha.domain.eventcourtreservation.dto.response.EventCourtReservationResponseDTO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;

public class EventCourtReservationResponseMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static EventCourtReservationResponseDTO toDTO(EventCourtReservation entity) {
        EventCourtReservationResponseDTO dto = new EventCourtReservationResponseDTO();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        if (entity.getEventRequest() != null) dto.setEventRequestId(entity.getEventRequest().getId());
        if (entity.getCourt() != null) dto.setCourtId(entity.getCourt().getId());
        return dto;
    }

}