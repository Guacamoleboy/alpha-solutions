package alpha.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties
public class MemberRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "first_name": "Name",
    //          "last_name": "Lastname",
    //          "email": "mail@mail.dk",
    //          "password": "password",
    //          "phone": "+4560606060",
    //          "date_of_birth": "1990-05-15",
    //          "gender": "gender",
    //          "membership_id": 1
    //      }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 12/09-2026

    // _________________________________________________________________________________________________________________

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("membership_id")
    private Integer membershipId;

}