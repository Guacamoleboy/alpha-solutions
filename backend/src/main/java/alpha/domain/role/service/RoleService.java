package alpha.domain.role.service;

import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class RoleService extends EntityManagerService<Role> {

    // Attributes
    private final RoleDAO roleDAO;

    // _________________________________________________________________________________________________________________

    public RoleService(EntityManager em) {
        super(new RoleDAO(em), Role.class);
        this.roleDAO = (RoleDAO) this.entityManagerDAO;
    }

    // _________________________________________________________________________________________________________________

    public Role getByName(RoleName name) {
        return roleDAO.getByName(name);
    }

}