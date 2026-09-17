package alpha.domain.settings.operatinghour.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatingHourResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "day_of_week": "MONDAY",
    //          "open_time": "08:00",
    //          "close_time": "21:00",
    //          "closed": false
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("day_of_week")
    private DayOfWeek dayOfWeek;

    @JsonProperty("open_time")
    private LocalTime openTime;

    @JsonProperty("close_time")
    private LocalTime closeTime;

    @JsonProperty("closed")
    private Boolean closed;

}