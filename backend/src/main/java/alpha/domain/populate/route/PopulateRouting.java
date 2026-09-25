package alpha.domain.populate.route;

import alpha.domain.populate.controller.PopulateController;
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

                post("/restart", populateController::restart);
                post("/membership", populateController::populateMembership);
                post("/members", populateController::populateMembers);
                post("/courts", populateController::populateCourts);
                post("/bookings", populateController::populateBookings);

            });

        };

    }

}