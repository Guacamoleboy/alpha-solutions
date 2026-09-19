package alpha.domain.member.dao;

import alpha.domain.member.entity.Member;
import alpha.dao.EntityManagerDAO;
import jakarta.persistence.EntityManager;

public class MemberDAO extends EntityManagerDAO<Member> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MemberDAO(EntityManager em){
        super(em, Member.class);
    }

    public Member refresh(Member member) {
        return executeQuery(() -> {
            em.refresh(member);
            return member;
        });
    }

}
