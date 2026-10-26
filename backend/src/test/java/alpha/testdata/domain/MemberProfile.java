package alpha.testdata.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberProfile {

    JONAS("jonas"),
    TINE("tine"),
    DENNIS("dennis"),
    KAMILLA("kamilla"),
    JORGEN("jorgen"),
    PASSWORD_CHANGE("passwordChange"),
    WRONG_PASSWORD("wrongPassword"),
    NULL_OPTIONAL_FIELDS("nullOptionalFields"),
    EVENT_MEMBER("eventMember");

    private final String key;
    
}