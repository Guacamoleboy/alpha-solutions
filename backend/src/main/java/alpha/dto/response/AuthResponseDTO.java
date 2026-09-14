package alpha.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class AuthResponseDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Output
    // ____________________
    //
    //      {
    //          "access_token": "eyJhbGciOiJIUzI1NiJ9...",
    //          "refresh_token": "eyJhbGciOiJIUzI1NiJ9...",
    //          "member": {
    //              "id": 1,
    //              "first_name": "Name",
    //              "last_name": "Lastname",
    //              "email": "mail@mail.dk",
    //              "phone": "+4560606060",
    //              "date_of_birth": "1990-05-15",
    //              "gender": "gender",
    //              "membership_id": 1,
    //              "last_played": "2026-09-11"
    //          }
    //      }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 14/09-2026

    // _________________________________________________________________________________________________________________

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("member")
    private MemberResponseDTO member;

}