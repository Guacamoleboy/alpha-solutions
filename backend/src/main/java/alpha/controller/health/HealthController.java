package alpha.controller.health;

import alpha.service.health.HealthService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import java.util.Map;

public class HealthController {

    // Attributes
    private final EntityManager em;
    private final HealthService healthService;

    // _________________________________________________________________________________________________________________

    public HealthController(EntityManager em) {
        this.em = em;
        this.healthService = new HealthService(em);
    }

    // _________________________________________________________________________________________________________________

    public void getHealth(Context ctx) {
        String result = healthService.checkHealth();
        ctx.json(Map.of(
                "status", "success",
                "message", result
        ));
    }

}