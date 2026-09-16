package alpha.domain.membership.service;

import alpha.domain.membership.dao.MembershipDAO;
import alpha.domain.membership.entity.Membership;
import alpha.service.EntityManagerService;
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
