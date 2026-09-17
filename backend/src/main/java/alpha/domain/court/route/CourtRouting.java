package alpha.domain.court.route;

import alpha.domain.court.controller.CourtController;
import alpha.domain.court.entity.Court;
import alpha.domain.court.service.CourtService;
import alpha.crud.CRUDRouting;
import jakarta.persistence.EntityManagerFactory;

public class CourtRouting extends CRUDRouting<Court> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public CourtRouting(EntityManagerFactory emf) {
        super("/court", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    @Override
    protected void customRoutes() {
        CourtController courtController = (CourtController) controller;
        // Custom endpoints here
        // Example: post("/path", controller::method);
    }

    // _________________________________________________________________________________________________________________

    private static CourtController createController(EntityManagerFactory emf) {
        return new CourtController(new CourtService(emf.createEntityManager()));
    }

}