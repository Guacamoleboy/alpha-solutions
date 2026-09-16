package alpha.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class RefreshTokenRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // ___________________
    //
    //      {
    //          "refresh_token": "eyJhbGciOiJIUzI1NiJ9..."
    //      }
    //
    // ____________________
    // Tested: YES
    // Last Tested: 14/09-2026

    // _________________________________________________________________________________________________________________

    @JsonProperty("refresh_token")
    private String refreshToken;

}
