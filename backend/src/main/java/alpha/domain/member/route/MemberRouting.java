package alpha.domain.member.route;

import alpha.domain.member.controller.MemberController;
import alpha.domain.member.entity.Member;
import alpha.domain.member.service.MemberService;
import alpha.crud.CRUDRouting;
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
