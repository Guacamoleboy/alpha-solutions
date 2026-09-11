package alpha.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties
public class MemberResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "id": 1,
    //          "first_name": "Name",
    //          "last_name": "Lastname",
    //          "email": "mail@mail.dk",
    //          "phone": "+4560606060",
    //          "date_of_birth": "1990-05-15",
    //          "gender": "gender",
    //          "membership_id": 1,
    //          "last_played": "2026-09-11"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________


    // _________________________________________________________________________________________________________________
    // JSON Fields

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

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("membership_id")
    private Integer membershipId;

    @JsonProperty("last_played")
    private LocalDate lastPlayed;

}