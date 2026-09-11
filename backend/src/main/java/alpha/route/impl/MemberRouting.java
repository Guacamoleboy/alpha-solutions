package alpha.route.impl;

import alpha.controller.impl.MemberController;
import alpha.entity.Member;
import alpha.service.internal.MemberService;
import jakarta.persistence.EntityManagerFactory;

public class MemberRouting extends CRUDRouting<Member> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MemberRouting(EntityManagerFactory emf) {
        super("/member", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    private static MemberController createController(EntityManagerFactory emf) {
        return new MemberController(new MemberService(emf.createEntityManager()));
    }

}