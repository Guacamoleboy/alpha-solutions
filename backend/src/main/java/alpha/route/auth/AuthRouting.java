package alpha.route.auth;

import alpha.controller.auth.AuthController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import static io.javalin.apibuilder.ApiBuilder.*;

public class AuthRouting {

    // Attributes
    private final AuthController authController;

    // _________________________________________________________________________________________________________________

    public AuthRouting(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        this.authController = new AuthController(em);
    }

    // _________________________________________________________________________________________________________________

    public EndpointGroup routes() {

        return () -> {

            // Route Endpoints
            path("/auth", () -> {

                // -------------------------------------------------------------------

                post("/register", authController::register);
                post("/login", authController::login);
                post("/refresh", authController::refresh);
                get("/me", authController::me);

            });

        };

    }

}