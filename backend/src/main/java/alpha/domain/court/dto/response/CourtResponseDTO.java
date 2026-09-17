package alpha.domain.court.dto.response;

import alpha.domain.court.enums.CourtSurface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@JsonIgnoreProperties
public class CourtResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "name": "Court 1",
    //          "active": true,
    //          "surface": "SYNTHETIC",
    //          "latitude": 55.676098,
    //          "longitude": 12.568337,
    //          "orientation_degrees": 90.00,
    //          "elevation": 12.50,
    //          "required_membership_id": 2,
    //          "required_membership_name": "premium",
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

    @JsonProperty("required_membership_name")
    private String requiredMembershipName;

    @JsonProperty("created_at")
    private Timestamp createdAt;

}