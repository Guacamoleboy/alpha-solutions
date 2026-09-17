package alpha.domain.settings.operatinghour.mapper.request;

import alpha.domain.settings.operatinghour.dto.request.OperatingHourRequestDTO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import java.time.DayOfWeek;

public class OperatingHourRequestMapper {

    // _________________________________________________________________________________________________________________

    // Maps OperatingHoursRequestDTO to OperatingHours entity
    // ______________________________________________________
    //
    //       OperatingHoursRequestDTO
    //                  ↓
    //       OperatingHoursRequestMapper
    //                  ↓
    //       OperatingHours
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static OperatingHour toEntity(OperatingHourRequestDTO dto, DayOfWeek dayOfWeek) {

        OperatingHour operatingHours = new OperatingHour();

        operatingHours.setDayOfWeek(dayOfWeek);
        operatingHours.setOpenTime(dto.getOpenTime());
        operatingHours.setCloseTime(dto.getCloseTime());
        operatingHours.setClosed(dto.getClosed());

        return operatingHours;

    }

}