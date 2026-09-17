package alpha.domain.court.mapper.response;

import alpha.domain.court.dto.response.CourtResponseDTO;
import alpha.domain.court.entity.Court;

public class CourtResponseMapper {

    // _________________________________________________________________________________________________________________

    // Maps Court entity to CourtResponseDTO
    // _____________________________________
    //
    //       Court
    //               ↓
    //       CourtResponseMapper
    //               ↓
    //       CourtResponseDTO
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static CourtResponseDTO toDTO(Court court) {

        CourtResponseDTO dto = new CourtResponseDTO();

        dto.setId(court.getId());
        dto.setName(court.getName());
        dto.setActive(court.getActive());
        dto.setSurface(court.getSurface());
        dto.setLatitude(court.getLatitude());
        dto.setLongitude(court.getLongitude());
        dto.setOrientationDegrees(court.getOrientationDegrees());
        dto.setElevation(court.getElevation());

        if (court.getRequiredMembership() != null) {
            dto.setRequiredMembershipId(
                    court.getRequiredMembership().getId()
            );

            dto.setRequiredMembershipName(
                    court.getRequiredMembership().getName()
            );
        }

        dto.setCreatedAt(court.getCreatedAt());

        return dto;

    }

}