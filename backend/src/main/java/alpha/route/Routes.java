package alpha.route;

import alpha.route.auth.AuthRouting;
import alpha.route.health.HealthRouting;
import alpha.route.impl.MemberRouting;
import alpha.route.impl.MembershipRouting;
import alpha.route.populate.PopulateRouting;
import alpha.route.status.StatusRouting;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

public class Routes {

    // Attributes

    // _______________________________________________________________________

    public static EndpointGroup registerRoutes(EntityManagerFactory entityManagerFactory) {

        // Routings
        StatusRouting statusRouting = new StatusRouting(entityManagerFactory);
        HealthRouting healthRouting = new HealthRouting(entityManagerFactory);
        MemberRouting memberRouting = new MemberRouting(entityManagerFactory);
        MembershipRouting membershipRouting = new MembershipRouting(entityManagerFactory);
        AuthRouting authRouting = new AuthRouting(entityManagerFactory);
        PopulateRouting populateRouting = new PopulateRouting(entityManagerFactory);

        // EndpointGroup Return to server
        return () -> {
            statusRouting.routes().addEndpoints();
            healthRouting.routes().addEndpoints();
            memberRouting.routes().addEndpoints();
            membershipRouting.routes().addEndpoints();
            authRouting.routes().addEndpoints();
            populateRouting.routes().addEndpoints();
        };

    }

}