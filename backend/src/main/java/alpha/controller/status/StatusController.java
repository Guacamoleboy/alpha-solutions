package alpha.controller.status;

import alpha.service.status.StatusService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import java.util.Map;

public class StatusController {

    // Attributes
    private final EntityManager em;
    private final StatusService statusService = new StatusService();

    // _________________________________________________________________________________________________________________

    public StatusController(EntityManager em) {
        this.em = em;
    }

    // _________________________________________________________________________________________________________________

    public void getStatus(Context ctx) {
        Map<String, Object> result = statusService.getStatus();
        ctx.status(200).json(Map.of(
                "status", "success",
                "message", result
        ));
    }

}