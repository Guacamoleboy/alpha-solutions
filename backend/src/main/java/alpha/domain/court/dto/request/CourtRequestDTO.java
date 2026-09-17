package alpha.domain.court.dto.request;

import alpha.domain.court.enums.CourtSurface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties
public class CourtRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "name": "Court 1",
    //          "active": true,
    //          "surface": "SYNTHETIC",
    //          "latitude": 55.676098,
    //          "longitude": 12.568337,
    //          "orientation_degrees": 90.00,
    //          "elevation": 12.50,
    //          "required_membership_id": 2
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("surface")
    private CourtSurface surface;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("orientation_degrees")
    private BigDecimal orientationDegrees;

    @JsonProperty("elevation")
    private BigDecimal elevation;

    @JsonProperty("required_membership_id")
    private Integer requiredMembershipId;

}