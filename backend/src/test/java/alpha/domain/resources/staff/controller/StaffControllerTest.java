package alpha.domain.resources.staff.controller;

import alpha.ATest;
import alpha.domain.resources.staff.dao.StaffDAO;
import alpha.domain.resources.staff.entity.Staff;
import alpha.domain.role.enums.RoleName;
import alpha.util.BCryptHash;
import alpha.testdata.TestDataFactory;
import alpha.testdata.TestJsonData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaffControllerTest extends ATest {

    // Attributes
    private StaffDAO staffDAO;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupStaffController() {
        staffDAO = new StaffDAO(em);
        staffDAO.deleteAll();
        startServer();

        List<String> serverRoles = RestAssured
                .given()
                .get("/role/all")
                .then()
                .statusCode(200)
                .extract()
                .path("data.name");

        if (serverRoles == null || !serverRoles.contains(RoleName.STAFF.name())) {
            RestAssured
                    .given()
                    .contentType("application/json")
                    .body(TestJsonData.body("role.json", "named", Map.of("name", "STAFF")))
                    .when()
                    .post("/role/")
                    .then()
                    .statusCode(200);
        }

        new TestDataFactory(em).role(RoleName.STAFF);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateStaffWithDefaultPasswordAndStaffRole() {
        Integer staffId = RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.bodyFromSample("staff.json", "create", "kamilla", Map.of()))
                .when()
                .post("/staff/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Kamilla"))
                .body("data.last_name", equalTo("Andersen"))
                .body("data.role", equalTo("STAFF"))
                .body("data.password_hashed", nullValue())
                .extract()
                .path("data.id");

        Staff createdStaff = staffDAO.getById(staffId);

        assertTrue(BCryptHash.check("Password12345!", createdStaff.getPasswordHashed()));
        assertTrue(createdStaff.getRole().getName() == RoleName.STAFF);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldUpdateStaffDetailsWithoutReplacingPasswordOrRole() {
        Integer staffId = RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.bodyFromSample("staff.json", "createWithoutPhone", "jorgen", Map.of()))
                .when()
                .post("/staff/")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        String originalPasswordHash = staffDAO.getById(staffId).getPasswordHashed();

        RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.bodyFromSample("staff.json", "update", "updatedJorgen", Map.of()))
                .when()
                .put("/staff/" + staffId)
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Updated J\u00f8rgen"))
                .body("data.salary", equalTo(30000.00F))
                .body("data.working_hours_weekly", equalTo(37.0F))
                .body("data.role", equalTo("STAFF"));

        em.clear();
        Staff updatedStaff = staffDAO.getById(staffId);

        assertTrue(originalPasswordHash.equals(updatedStaff.getPasswordHashed()));
        assertTrue(updatedStaff.getRole().getName() == RoleName.STAFF);
        assertTrue(updatedStaff.getSalary().compareTo(new BigDecimal("30000.00")) == 0);
    }

}
