// Pathing
// _______
// src/main/java/alpha/domain/role/route/RoleRouting.java

package alpha.domain.role.route;

import alpha.crud.CRUDRouting;
import alpha.domain.role.controller.RoleController;
import alpha.domain.role.entity.Role;
import alpha.domain.role.service.RoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class RoleRouting extends CRUDRouting<Role> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public RoleRouting(EntityManagerFactory emf) {
        super("/role", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    private static RoleController createController(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        RoleService roleService = new RoleService(em);
        return new RoleController(roleService);
    }

}