package alpha.domain.role.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import jakarta.persistence.EntityManager;

public class RoleDAO extends EntityManagerDAO<Role> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public RoleDAO(EntityManager em) {
        super(em, Role.class);
    }

    // _________________________________________________________________________________________________________________

    public Role getByName(RoleName name) {
        return findEntityByColumn(name, Role.Fields.NAME);
    }

}