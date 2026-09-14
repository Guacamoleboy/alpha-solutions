package alpha.route.populate;

import alpha.controller.populate.PopulateController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import static io.javalin.apibuilder.ApiBuilder.*;

public class PopulateRouting {

    // Attributes
    private final PopulateController populateController;

    // _________________________________________________________________________________________________________________

    public PopulateRouting(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        this.populateController = new PopulateController(em);
    }

    // _________________________________________________________________________________________________________________

    public EndpointGroup routes() {

        return () -> {

            path("/populate", () -> {

                // -------------------------------------------------------------------

                post("", populateController::populate);

            });

        };

    }

}