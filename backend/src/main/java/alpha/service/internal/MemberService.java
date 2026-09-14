package alpha.service.internal;

import alpha.dao.impl.MemberDAO;
import alpha.dto.request.MemberRequestDTO;
import alpha.dto.response.MemberResponseDTO;
import alpha.entity.Member;
import alpha.exception.ApiException;
import alpha.mapper.response.MemberResponseMapper;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;

public class MemberService extends EntityManagerService<Member> {

    // Attributes
    private final MemberDAO memberDAO;

    // _________________________________________________________________________________________________________________

    public MemberService(EntityManager em){
        super(new MemberDAO(em), Member.class);
        this.memberDAO = (MemberDAO) this.entityManagerDAO;
    }

    // _________________________________________________________________________________________________________________

    public Member createMember(Member member) {

        validateNotEmpty(member.getFirstName(), "Member.firstName");
        validateNotEmpty(member.getLastName(), "Member.lastName");
        validateNotEmpty(member.getEmail(), "Member.email");
        validateNotEmpty(member.getPasswordHashed(), "Member.password");

        // Password hashing
        member.setPasswordHashed(BCrypt.hashpw(member.getPasswordHashed(), BCrypt.gensalt()));

        return super.create(member);
    }

    // _________________________________________________________________________________________________________________

    public boolean existsById(Integer memberId) {
        validateNotEmpty(memberId, "Member.id");
        return memberDAO.existByColumn(
                memberId,
                Member.Fields.ID
        );
    }

    // _________________________________________________________________________________________________________________
    // Not a good method right now. But it works for sprint 0.

    public MemberResponseDTO update(Integer id, MemberRequestDTO dto) {
        Member member = memberDAO.getById(id);

        if (member == null) {
            throw new ApiException(404, "Member not found");
        }

        if (dto.getFirstName() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.FIRST_NAME,
                    dto.getFirstName()
            );
        }
        if (dto.getLastName() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.LAST_NAME,
                    dto.getLastName()
            );
        }
        if (dto.getEmail() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.EMAIL,
                    dto.getEmail()
            );
        }
        if (dto.getPhone() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.PHONE,
                    dto.getPhone()
            );
        }
        if (dto.getDateOfBirth() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.DATE_OF_BIRTH,
                    dto.getDateOfBirth()
            );
        }
        if (dto.getGender() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.GENDER,
                    dto.getGender()
            );
        }

        return MemberResponseMapper.toDTO(memberDAO.getById(id));
    }

}