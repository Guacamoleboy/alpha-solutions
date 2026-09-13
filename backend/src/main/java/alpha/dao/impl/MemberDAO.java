package alpha.dao.impl;

import alpha.entity.Member;
import jakarta.persistence.EntityManager;

public class MemberDAO extends EntityManagerDAO<Member> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MemberDAO(EntityManager em){
        super(em, Member.class);
    }

}