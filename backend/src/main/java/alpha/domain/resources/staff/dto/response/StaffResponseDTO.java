package alpha.domain.resources.staff.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import alpha.domain.role.enums.RoleName;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@JsonIgnoreProperties
public class StaffResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "first_name": "Name",
    //          "last_name": "Lastname",
    //          "email": "staff@mail.dk",
    //          "phone": "+4560606060",
    //          "salary": 25000.00,
    //          "working_hours_weekly": 37.5,
    //          "role": "STAFF",
    //          "created_at": "2026-09-20T12:00:00"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("id")
    private Integer id;

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

    @JsonProperty("role")
    private RoleName role;

    @JsonProperty("created_at")
    private Timestamp createdAt;

}