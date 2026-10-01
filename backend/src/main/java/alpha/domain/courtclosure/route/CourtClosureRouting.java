package alpha.domain.courtclosure.route;

import alpha.crud.CRUDRouting;
import alpha.domain.courtclosure.controller.CourtClosureController;
import alpha.domain.courtclosure.entity.CourtClosure;
import alpha.domain.courtclosure.service.CourtClosureService;
import jakarta.persistence.EntityManagerFactory;

public class CourtClosureRouting extends CRUDRouting<CourtClosure> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public CourtClosureRouting(EntityManagerFactory emf) {
        super("/court-closures", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    private static CourtClosureController createController(EntityManagerFactory emf) {
        return new CourtClosureController(new CourtClosureService(emf.createEntityManager()));
    }

}