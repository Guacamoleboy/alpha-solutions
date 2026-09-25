package alpha.domain.populate.controller;

import alpha.domain.populate.PopulateDB;
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

    public void populateMembership(Context ctx) {
        TryCatchHelper.tryCatchHelper(
                ctx,
                () -> PopulateDB.populateMembership(em),
                "Memberships checked"
        );
    }

    // _________________________________________________________________________________________________________________

    public void restart(Context ctx) {
        TryCatchHelper.tryCatchHelper(
                ctx,
                () -> PopulateDB.restart(em),
                "Database restarted"
        );
    }

    // _________________________________________________________________________________________________________________

    public void populateMembers(Context ctx) {
        TryCatchHelper.tryCatchHelperVoid(ctx, () -> {
            PopulateDB.populateMembers(em);
        }, "Members populated");
    }

    // _________________________________________________________________________________________________________________

    public void populateCourts(Context ctx) {
        TryCatchHelper.tryCatchHelper(
                ctx,
                () -> PopulateDB.populateCourts(em),
                "Courts checked"
        );
    }

    // _________________________________________________________________________________________________________________

    public void populateBookings(Context ctx) {
        TryCatchHelper.tryCatchHelperVoid(ctx, () -> {
            PopulateDB.populateBookings(em);
        }, "Bookings populated");
    }

}