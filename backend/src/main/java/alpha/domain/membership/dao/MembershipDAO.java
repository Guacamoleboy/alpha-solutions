package alpha.domain.membership.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.membership.entity.Membership;
import jakarta.persistence.EntityManager;

public class MembershipDAO extends EntityManagerDAO<Membership> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MembershipDAO(EntityManager em){
        super(em, Membership.class);
    }

}
