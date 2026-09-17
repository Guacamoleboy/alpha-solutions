package alpha.domain.court.service;

import alpha.domain.court.dao.CourtDAO;
import alpha.domain.court.entity.Court;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class CourtService extends EntityManagerService<Court> {

    // Attributes
    private final CourtDAO courtDAO;

    // _________________________________________________________________________________________________________________

    public CourtService(EntityManager em) {
        super(new CourtDAO(em), Court.class);
        this.courtDAO = (CourtDAO) this.entityManagerDAO;
    }

}