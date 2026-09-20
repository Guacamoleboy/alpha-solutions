package alpha.domain.role.controller;

import alpha.ATest;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

class RoleControllerTest extends ATest {

    // Attributes
    private RoleDAO roleDAO;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupRoleController() {
        roleDAO = new RoleDAO(em);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateAndUpdateRoleThroughController() {
        startServer();

        Role staffRole = roleDAO.getByName(RoleName.STAFF);
        if (staffRole == null) {
            Integer roleId = RestAssured
                    .given()
                    .contentType("application/json")
                    .body("{\"name\":\"STAFF\"}")
                    .when()
                    .post("/role/")
                    .then()
                    .statusCode(200)
                    .body("status", equalTo("success"))
                    .body("data.name", equalTo("STAFF"))
                    .extract()
                    .path("data.id");
            staffRole = roleDAO.getById(roleId);
        }

        RestAssured
                .given()
                .contentType("application/json")
                .body("{\"name\":\"STAFF\"}")
                .when()
                .put("/role/" + staffRole.getId())
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.id", equalTo(staffRole.getId()))
                .body("data.name", equalTo("STAFF"));
    }

}