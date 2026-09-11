package alpha.mapper.response;

import alpha.dto.response.MemberResponseDTO;
import alpha.entity.Member;

public class MemberResponseMapper {

    // _________________________________________________________________________________________________________________

    // Maps Member entity to MemberResponseDTO
    // _______________________________________
    //
    //             Member
    //               ↓
    //      MemberResponseMapper
    //               ↓
    //      MemberResponseDTO
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static MemberResponseDTO toDTO(Member member) {

        MemberResponseDTO dto = new MemberResponseDTO();

        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setDateOfBirth(member.getDateOfBirth());
        dto.setGender(member.getGender());

        if (member.getMembership() != null) {
            dto.setMembershipId(member.getMembership().getId());
        }

        dto.setLastPlayed(member.getLastPlayed());

        return dto;
    }

}