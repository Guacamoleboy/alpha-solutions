package alpha.domain.eventorganizer.mapper.request;

import alpha.domain.eventorganizer.dto.request.EventOrganizerRequestDTO;
import alpha.domain.eventorganizer.entity.EventOrganizer;

public class EventOrganizerRequestMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static EventOrganizer toEntity(EventOrganizerRequestDTO dto) { 
        return new EventOrganizer();
    }

}