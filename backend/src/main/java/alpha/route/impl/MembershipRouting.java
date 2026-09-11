package alpha.route.impl;

import alpha.controller.impl.MembershipController;
import alpha.entity.Membership;
import alpha.service.internal.MembershipService;
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