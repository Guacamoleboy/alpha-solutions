package alpha.domain.courtclosure.mapper.response;

import alpha.domain.courtclosure.dto.response.CourtClosureResponseDTO;
import alpha.domain.courtclosure.entity.CourtClosure;

public class CourtClosureResponseMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static CourtClosureResponseDTO toDTO(CourtClosure entity) {
        CourtClosureResponseDTO dto = new CourtClosureResponseDTO();
        dto.setId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setReason(entity.getReason());
        if (entity.getCourt() != null) {
            dto.setCourtId(entity.getCourt().getId());
            dto.setCourtName(entity.getCourt().getName());
        }
        return dto;
    }

}