package alpha.testdata.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourtProfile {

    ACTIVE("active"),
    INACTIVE("inactive");

    private final String key;

}