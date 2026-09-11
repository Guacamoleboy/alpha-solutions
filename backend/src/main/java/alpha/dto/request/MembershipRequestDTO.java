package alpha.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties
public class MembershipRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // __________________
    //
    //      {
    //          "name": "Premium",
    //          "description": "Text",
    //          "price": 299.95,
    //          "currency": "DKK",
    //          "duration": "30 days",
    //          "start_date": "2026-09-01",
    //          "end_date": "2026-09-30",
    //          "guest_pass": true
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

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

}