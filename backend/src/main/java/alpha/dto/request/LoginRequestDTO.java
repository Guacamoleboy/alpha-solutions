package alpha.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class LoginRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "email": "mail@mail.dk",
    //          "password": "password"
    //      }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 14/09-2026

    // _________________________________________________________________________________________________________________

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}