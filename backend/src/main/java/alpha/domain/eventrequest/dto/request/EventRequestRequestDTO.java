package alpha.domain.eventrequest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties
public class EventRequestRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "name": "Autumn Event",
    //          "start_time": "2026-09-29T11:00:00",
    //          "end_time": "2026-09-29T13:00:00",
    //          "guest_count": 20,
    //          "requested_court_count": 4,
    //          "equipment_required": false,
    //          "event_code": "AUTUMN26",
    //          "organizer_emails": [
    //              "test2@test.dk",
    //              "test3@test.dk"
    //          ]
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

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

    @JsonProperty("organizer_emails")
    private List<String> organizerEmails;

}
