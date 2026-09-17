package alpha.domain.settings.operatinghour.service;

import alpha.domain.settings.operatinghour.dao.OperatingHourDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class OperatingHourService extends EntityManagerService<OperatingHour> {

    // Attributes
    private final OperatingHourDAO operatingHourDAO;

    // _________________________________________________________________________________________________________________

    public OperatingHourService(EntityManager em) {
        super(new OperatingHourDAO(em), OperatingHour.class);
        this.operatingHourDAO = (OperatingHourDAO) this.entityManagerDAO;
    }

}