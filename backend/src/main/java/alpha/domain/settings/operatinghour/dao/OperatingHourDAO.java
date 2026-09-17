package alpha.domain.settings.operatinghour.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import jakarta.persistence.EntityManager;

public class OperatingHourDAO extends EntityManagerDAO<OperatingHour> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public OperatingHourDAO(EntityManager em) {
        super(em, OperatingHour.class);
    }

}