package alpha.domain.member.route;

import alpha.domain.member.controller.MemberController;
import alpha.domain.member.entity.Member;
import alpha.domain.member.service.MemberService;
import alpha.crud.CRUDRouting;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class MemberRouting extends CRUDRouting<Member> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MemberRouting(EntityManagerFactory emf) {
        super("/member", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    @Override
    protected void customRoutes() {
        MemberController memberController = (MemberController) controller;
        post("/password", memberController::updatePassword);
        post("/password/forgot/verify", memberController::verifyForgottenPassword);
        post("/password/forgot/reset", memberController::resetForgottenPassword);
    }

    // _________________________________________________________________________________________________________________

    private static MemberController createController(EntityManagerFactory emf) {
        return new MemberController(new MemberService(emf.createEntityManager()));
    }

}