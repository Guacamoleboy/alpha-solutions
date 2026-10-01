package alpha.domain.courtclosure.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import alpha.domain.courtclosure.enums.CourtClosureReason;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties
public class CourtClosureResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "court_id": 2,
    //          "court_name": "Court 2",
    //          "start_time": "2026-10-04T13:00:00",
    //          "end_time": "2026-10-04T17:00:00",
    //          "reason": "MAINTENANCE"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("court_id")
    private Integer courtId;

    @JsonProperty("court_name")
    private String courtName;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("reason")
    private CourtClosureReason reason;

}