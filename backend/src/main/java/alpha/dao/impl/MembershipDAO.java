package alpha.dao.impl;

import alpha.entity.Membership;
import jakarta.persistence.EntityManager;

public class MembershipDAO extends EntityManagerDAO<Membership> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MembershipDAO(EntityManager em){
        super(em, Membership.class);
    }

}