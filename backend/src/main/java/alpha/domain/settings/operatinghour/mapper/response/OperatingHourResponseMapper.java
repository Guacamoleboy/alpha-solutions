package alpha.domain.settings.operatinghour.mapper.response;

import alpha.domain.settings.operatinghour.dto.response.OperatingHourResponseDTO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;

public class OperatingHourResponseMapper {

    // _________________________________________________________________________________________________________________

    // Maps OperatingHours entity to OperatingHoursResponseDTO
    // _____________________________________________________
    //
    //       OperatingHours
    //                  ↓
    //       OperatingHoursResponseMapper
    //                  ↓
    //       OperatingHoursResponseDTO
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static OperatingHourResponseDTO toDTO(OperatingHour operatingHours) {

        OperatingHourResponseDTO dto = new OperatingHourResponseDTO();

        dto.setId(operatingHours.getId());
        dto.setDayOfWeek(operatingHours.getDayOfWeek());
        dto.setOpenTime(operatingHours.getOpenTime());
        dto.setCloseTime(operatingHours.getCloseTime());
        dto.setClosed(operatingHours.getClosed());

        return dto;

    }

}