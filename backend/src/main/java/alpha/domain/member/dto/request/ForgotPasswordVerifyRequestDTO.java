package alpha.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ForgotPasswordVerifyRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "email": "mail@mail.dk",
    //          "date_of_birth": "1990-05-15"
    //      }
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    @JsonProperty("email")
    private String email;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

}