package alpha.domain.court.mapper.request;

import alpha.domain.court.dto.request.CourtRequestDTO;
import alpha.domain.court.entity.Court;

public class CourtRequestMapper {

    // _________________________________________________________________________________________________________________

    // Maps CourtRequestDTO to Court entity
    // ____________________________________
    //
    //       CourtRequestDTO
    //              ↓
    //       CourtRequestMapper
    //              ↓
    //       Court
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static Court toEntity(CourtRequestDTO dto) {

        Court court = new Court();

        court.setName(dto.getName());
        court.setActive(dto.getActive());
        court.setSurface(dto.getSurface());
        court.setLatitude(dto.getLatitude());
        court.setLongitude(dto.getLongitude());
        court.setOrientationDegrees(dto.getOrientationDegrees());
        court.setElevation(dto.getElevation());

        return court;

    }

}