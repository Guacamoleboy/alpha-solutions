package alpha.route;

import alpha.domain.auth.route.AuthRouting;
import alpha.domain.health.route.HealthRouting;
import alpha.domain.member.route.MemberRouting;
import alpha.domain.membership.route.MembershipRouting;
import alpha.domain.populate.route.PopulateRouting;
import alpha.domain.status.route.StatusRouting;
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
