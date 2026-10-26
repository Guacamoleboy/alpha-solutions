package alpha.domain.role.dao;

import alpha.ATest;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.testdata.TestDataFactory;
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
        Role staffRole = new TestDataFactory(em).role(RoleName.STAFF);

        Role foundRole = roleDAO.getByName(RoleName.STAFF);

        assertNotNull(foundRole);
        assertEquals(staffRole.getId(), foundRole.getId());
        assertEquals(RoleName.STAFF, foundRole.getName());
    }

}
