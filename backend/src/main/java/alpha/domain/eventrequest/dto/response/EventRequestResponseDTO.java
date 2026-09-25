package alpha.domain.eventrequest.dto.response;

import alpha.domain.eventrequest.enums.EventRequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties
public class EventRequestResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "name": "Autumn Event",
    //          "start_time": "2026-09-29T11:00:00",
    //          "end_time": "2026-09-29T13:00:00",
    //          "guest_count": 20,
    //          "requested_court_count": 4,
    //          "equipment_required": false,
    //          "event_code": "AUTUMN26",
    //          "status": "PENDING",
    //          "requester_id": 15,
    //          "created_at": "2026-09-25T12:30:00",
    //          "updated_at": "2026-09-25T12:30:00"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("guest_count")
    private Integer guestCount;

    @JsonProperty("requested_court_count")
    private Integer requestedCourtCount;

    @JsonProperty("equipment_required")
    private Boolean equipmentRequired;

    @JsonProperty("event_code")
    private String eventCode;

    @JsonProperty("status")
    private EventRequestStatus status;

    @JsonProperty("requester_id")
    private Integer requesterId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}