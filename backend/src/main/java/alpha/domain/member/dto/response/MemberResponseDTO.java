package alpha.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Timestamp;
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
    //          "membership_name": "basic",
    //          "last_played": "2026-09-11",
    //          "last_login": "2026-09-14T15:42:31",
    //          "created_at": "2026-09-14T15:42:31"
    //      }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 14/09-2026

    // _________________________________________________________________________________________________________________

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

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("membership_id")
    private Integer membershipId;

    @JsonProperty("membership_name")
    private String membershipName;

    @JsonProperty("last_played")
    private LocalDate lastPlayed;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("last_login")
    private Timestamp lastLogin;

}
