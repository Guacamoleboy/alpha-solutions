package alpha.domain.settings.operatinghour.service;

import alpha.ATest;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperatingHourServiceTest extends ATest {

    // Attributes
    private OperatingHourService operatingHourService;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupOperatingHourService() {
        operatingHourService = new OperatingHourService(em);
        operatingHourService.deleteAll();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindOperatingHourByDayOfWeek() {
        OperatingHour mondayHours = OperatingHour.builder()
                .dayOfWeek(DayOfWeek.MONDAY)
                .openTime(LocalTime.of(8, 0))
                .closeTime(LocalTime.of(21, 0))
                .closed(false)
                .build();
        operatingHourService.create(mondayHours);

        OperatingHour foundHours = operatingHourService.getByDayOfWeek(DayOfWeek.MONDAY);

        assertNotNull(foundHours);
        assertEquals(mondayHours.getId(), foundHours.getId());
        assertEquals(LocalTime.of(8, 0), foundHours.getOpenTime());
    }

}