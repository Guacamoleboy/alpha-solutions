package alpha.domain.eventorganizer.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties
public class EventOrganizerResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "event_request_id": 1,
    //          "member_id": 16,
    //          "created_at": "2026-09-25T12:30:00"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("event_request_id")
    private Integer eventRequestId;

    @JsonProperty("member_id")
    private Integer memberId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}