package alpha.domain.membership.route;

import alpha.domain.membership.controller.MembershipController;
import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.service.MembershipService;
import alpha.crud.CRUDRouting;
import jakarta.persistence.EntityManagerFactory;

public class MembershipRouting extends CRUDRouting<Membership> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MembershipRouting(EntityManagerFactory emf) {
        super("/membership", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    private static MembershipController createController(EntityManagerFactory emf) {
        return new MembershipController(new MembershipService(emf.createEntityManager()));
    }

}
