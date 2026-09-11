package alpha.mapper.request;

import alpha.dto.request.MemberRequestDTO;
import alpha.entity.Member;

public class MemberRequestMapper {

    // _________________________________________________________________________________________________________________

    // Maps MemberRequestDTO to Member entity
    // ______________________________________
    //
    //      MemberRequestDTO
    //              ↓
    //      MemberRequestMapper
    //              ↓
    //             Member
    //
    // ____________________
    // Tested: YES
    // Last Tested: 12/09-2026

    // _________________________________________________________________________________________________________________

    public static Member toEntity(MemberRequestDTO dto) {

        Member member = new Member();

        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setEmail(dto.getEmail());
        member.setPasswordHashed(dto.getPassword());
        member.setPhone(dto.getPhone());
        member.setDateOfBirth(dto.getDateOfBirth());
        member.setGender(dto.getGender());

        return member;

    }

}