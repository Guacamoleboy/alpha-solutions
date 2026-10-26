package alpha.testdata.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingProfile {
    
    CONFIRMED("confirmed"),
    CANCELLED("cancelled");

    private final String key;

}