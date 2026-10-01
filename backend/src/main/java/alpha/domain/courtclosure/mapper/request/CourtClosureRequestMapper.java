package alpha.domain.courtclosure.mapper.request;

import alpha.domain.courtclosure.dto.request.CourtClosureRequestDTO;
import alpha.domain.courtclosure.entity.CourtClosure;

public class CourtClosureRequestMapper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static CourtClosure toEntity(CourtClosureRequestDTO dto) {
        return CourtClosure.builder()
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .reason(dto.getReason())
                .build();
    }

}