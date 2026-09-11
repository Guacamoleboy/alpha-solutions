package alpha.route;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

public class Routes {

    // Attributes

    // _______________________________________________________________________

    public static EndpointGroup registerRoutes(EntityManagerFactory entityManagerFactory) {

        // Routings
        // AuthRouting authRouting = new AuthRouting(entityManagerFactory);

        // EndpointGroup Return to server
        return () -> {
            // authRouting.routes().addEndpoints();
        };

    }

}