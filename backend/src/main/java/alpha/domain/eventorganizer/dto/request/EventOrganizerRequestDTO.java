package alpha.domain.eventorganizer.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class EventOrganizerRequestDTO {
    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "event_request_id": 1,
    //          "member_id": 16
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("event_request_id")
    private Integer eventRequestId;

    @JsonProperty("member_id")
    private Integer memberId;
}
