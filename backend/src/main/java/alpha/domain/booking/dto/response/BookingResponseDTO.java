package alpha.domain.booking.dto.response;

import alpha.domain.booking.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties
public class BookingResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "member_id": 15,
    //          "member_name": "Name Lastname",
    //          "court_id": 2,
    //          "court_name": "Court 2",
    //          "start_time": "2026-09-20T14:00:00",
    //          "end_time": "2026-09-20T15:00:00",
    //          "status": "CONFIRMED",
    //          "created_at": "2026-09-17T15:42:31"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("member_id")
    private Integer memberId;

    @JsonProperty("member_name")
    private String memberName;

    @JsonProperty("court_id")
    private Integer courtId;

    @JsonProperty("court_name")
    private String courtName;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("status")
    private BookingStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}