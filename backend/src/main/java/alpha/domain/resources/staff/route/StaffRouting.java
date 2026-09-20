package alpha.domain.resources.staff.route;

import alpha.crud.CRUDRouting;
import alpha.domain.resources.staff.controller.StaffController;
import alpha.domain.resources.staff.entity.Staff;
import alpha.domain.resources.staff.service.StaffService;
import jakarta.persistence.EntityManagerFactory;

public class StaffRouting extends CRUDRouting<Staff> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public StaffRouting(EntityManagerFactory emf) {
        super("/staff", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    private static StaffController createController(EntityManagerFactory emf) {
        return new StaffController(new StaffService(emf.createEntityManager()));
    }

}

