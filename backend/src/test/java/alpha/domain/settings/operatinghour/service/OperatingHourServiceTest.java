package alpha.domain.settings.operatinghour.service;

import alpha.ATest;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.testdata.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import alpha.testdata.domain.OperatingHourProfile;
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
        OperatingHour mondayHours = new TestDataFactory(em).operatingHour(OperatingHourProfile.MONDAY);

        OperatingHour foundHours = operatingHourService.getByDayOfWeek(DayOfWeek.MONDAY);

        assertNotNull(foundHours);
        assertEquals(mondayHours.getId(), foundHours.getId());
        assertEquals(mondayHours.getOpenTime(), foundHours.getOpenTime());
    }

}
