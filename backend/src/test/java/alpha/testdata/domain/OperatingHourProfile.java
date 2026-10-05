package alpha.testdata.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperatingHourProfile {

    MONDAY("monday"),
    EVENT_OPEN("eventOpen");

    private final String key;
    
}