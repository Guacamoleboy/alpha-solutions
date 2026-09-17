package alpha.domain.settings.operatinghour.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatingHourRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
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

    @JsonProperty("open_time")
    private LocalTime openTime;

    @JsonProperty("close_time")
    private LocalTime closeTime;

    @JsonProperty("closed")
    private Boolean closed;

}