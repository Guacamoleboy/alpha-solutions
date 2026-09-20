package alpha.domain.role.dao;

import alpha.ATest;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleDAOTest extends ATest {

    // Attributes
    private RoleDAO roleDAO;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupRoleDAO() {
        roleDAO = new RoleDAO(em);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindRoleByName() {
        Role staffRole = roleDAO.getByName(RoleName.STAFF);

        if (staffRole == null) {
            staffRole = roleDAO.create(Role.builder().name(RoleName.STAFF).build());
        }

        Role foundRole = roleDAO.getByName(RoleName.STAFF);

        assertNotNull(foundRole);
        assertEquals(staffRole.getId(), foundRole.getId());
        assertEquals(RoleName.STAFF, foundRole.getName());
    }

}