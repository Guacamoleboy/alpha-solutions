package alpha.domain.court.dao;

import alpha.domain.court.entity.Court;
import alpha.dao.EntityManagerDAO;
import jakarta.persistence.EntityManager;

public class CourtDAO extends EntityManagerDAO<Court> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public CourtDAO(EntityManager em) {
        super(em, Court.class);
    }

}