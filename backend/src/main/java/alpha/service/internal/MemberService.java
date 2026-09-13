package alpha.service.internal;

import alpha.dao.impl.MemberDAO;
import alpha.entity.Member;
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

}