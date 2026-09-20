package alpha.domain.resources.staff.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties
public class StaffRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "first_name": "Name",
    //          "last_name": "Lastname",
    //          "email": "staff@mail.dk",
    //          "phone": "+4560606060",
    //          "salary": 25000.00,
    //          "working_hours_weekly": 37.5
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("salary")
    private BigDecimal salary;

    @JsonProperty("working_hours_weekly")
    private BigDecimal workingHoursWeekly;

}