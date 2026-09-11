package alpha.mapper.request;

import alpha.dto.request.MembershipRequestDTO;
import alpha.entity.Membership;

public class MembershipRequestMapper {

    // _________________________________________________________________________________________________________________

    // Maps MembershipRequestDTO to Membership entity
    // _______________________________________________
    //
    //      MembershipRequestDTO
    //              ↓
    //      MembershipRequestMapper
    //              ↓
    //          Membership
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static Membership toEntity(MembershipRequestDTO dto) {

        Membership membership = new Membership();

        membership.setName(dto.getName());
        membership.setDescription(dto.getDescription());
        membership.setPrice(dto.getPrice());
        membership.setCurrency(dto.getCurrency());
        membership.setDuration(dto.getDuration());
        membership.setStartDate(dto.getStartDate());
        membership.setEndDate(dto.getEndDate());
        membership.setGuestPass(dto.getGuestPass());

        return membership;

    }

}