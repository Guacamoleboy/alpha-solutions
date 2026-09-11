package alpha.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties
public class MembershipResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "name": "Premium",
    //          "description": "Text",
    //          "price": 299.95,
    //          "currency": "DKK",
    //          "duration": "30 days",
    //          "start_date": "2026-09-01",
    //          "end_date": "2026-09-30",
    //          "guest_pass": true,
    //          "active": true
    //      }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 12/09-2026

    // _________________________________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("guest_pass")
    private Boolean guestPass;

    @JsonProperty("active")
    private Boolean active;

}