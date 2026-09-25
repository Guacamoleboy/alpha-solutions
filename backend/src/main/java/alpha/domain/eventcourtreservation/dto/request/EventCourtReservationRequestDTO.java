package alpha.domain.eventcourtreservation.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class EventCourtReservationRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "event_request_id": 1,
    //          "court_id": 4
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("event_request_id")
    private Integer eventRequestId;

    @JsonProperty("court_id")
    private Integer courtId;

}