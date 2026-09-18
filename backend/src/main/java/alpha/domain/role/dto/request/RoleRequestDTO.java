package alpha.domain.role.dto.request;

import alpha.domain.role.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class RoleRequestDTO {

    // _________________________________________________________________________________________________________________

    // Expected JSON Input
    // __________________
    //
    //      {
    //          "name": "MEMBER"
    //      }
    //
    // __________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @JsonProperty("name")
    private RoleName name;

}