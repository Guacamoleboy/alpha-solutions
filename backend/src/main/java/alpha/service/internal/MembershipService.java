package alpha.service.internal;

import alpha.dao.impl.MembershipDAO;
import alpha.entity.Membership;
import jakarta.persistence.EntityManager;

public class MembershipService extends EntityManagerService<Membership> {

    // Attributes
    private final MembershipDAO membershipDAO;

    // _________________________________________________________________________________________________________________

    public MembershipService(EntityManager em){
        super(new MembershipDAO(em), Membership.class);
        this.membershipDAO = (MembershipDAO) this.entityManagerDAO;
    }

}