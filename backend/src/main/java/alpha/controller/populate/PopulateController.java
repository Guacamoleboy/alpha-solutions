package alpha.controller.populate;

import alpha.util.PopulateDB;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;

public class PopulateController {

    // Attributes
    private final EntityManager em;

    // _________________________________________________________________________________________________________________

    public PopulateController(EntityManager em) {
        this.em = em;
    }

    // _________________________________________________________________________________________________________________

    public void populate(Context ctx) {
        TryCatchHelper.tryCatchHelperVoid(ctx, () -> {
            PopulateDB.populate(em);
        }, "Database populated");
    }

}