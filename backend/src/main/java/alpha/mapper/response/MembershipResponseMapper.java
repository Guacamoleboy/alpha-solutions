package alpha.mapper.response;

import alpha.dto.response.MembershipResponseDTO;
import alpha.entity.Membership;

public class MembershipResponseMapper {

    // _________________________________________________________________________________________________________________

    // Maps Membership entity to MembershipResponseDTO
    // _______________________________________________
    //
    //          Membership
    //              ↓
    //      MembershipResponseMapper
    //              ↓
    //      MembershipResponseDTO
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static MembershipResponseDTO toDTO(Membership membership) {

        MembershipResponseDTO dto = new MembershipResponseDTO();

        dto.setId(membership.getId());
        dto.setName(membership.getName());
        dto.setDescription(membership.getDescription());
        dto.setPrice(membership.getPrice());
        dto.setCurrency(membership.getCurrency());
        dto.setDuration(membership.getDuration());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setGuestPass(membership.getGuestPass());
        dto.setActive(membership.getActive());

        return dto;

    }

}