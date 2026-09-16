package alpha.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class MemberPasswordRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //  {
    //      "current_password": "OldPassword1!",
    //      "new_password": "NewPassword1!",
    //      "confirm_password": "NewPassword1!"
    //  }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 16/09-2026

    // _________________________________________________________________________________________________________________

    @JsonProperty("current_password")
    private String currentPassword;

    @JsonProperty("new_password")
    private String newPassword;

    @JsonProperty("confirm_password")
    private String confirmPassword;

}