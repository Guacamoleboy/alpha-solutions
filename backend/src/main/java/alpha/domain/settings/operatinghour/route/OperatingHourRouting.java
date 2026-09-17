package alpha.domain.settings.operatinghour.route;

import alpha.crud.CRUDRouting;
import alpha.domain.settings.operatinghour.controller.OperatingHourController;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import jakarta.persistence.EntityManagerFactory;

public class OperatingHourRouting extends CRUDRouting<OperatingHour> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public OperatingHourRouting(EntityManagerFactory emf) {
        super("/settings/operating-hours", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    @Override
    protected void customRoutes() {
        OperatingHourController operatingHoursController = (OperatingHourController) controller;
        // post("/password", memberController::updatePassword);
    }

    // _________________________________________________________________________________________________________________

    private static OperatingHourController createController(EntityManagerFactory emf) {
        return new OperatingHourController(new OperatingHourService(emf.createEntityManager()));
    }

}