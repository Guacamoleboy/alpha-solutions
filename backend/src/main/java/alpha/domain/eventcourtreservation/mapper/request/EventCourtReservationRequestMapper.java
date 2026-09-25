package alpha.domain.eventcourtreservation.mapper.request;

import alpha.domain.eventcourtreservation.dto.request.EventCourtReservationRequestDTO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;

public class EventCourtReservationRequestMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static EventCourtReservation toEntity(EventCourtReservationRequestDTO dto) {
        return new EventCourtReservation();
    }

}