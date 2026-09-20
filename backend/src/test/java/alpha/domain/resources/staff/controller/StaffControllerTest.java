package alpha.domain.resources.staff.controller;

import alpha.ATest;
import alpha.domain.resources.staff.dao.StaffDAO;
import alpha.domain.resources.staff.entity.Staff;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.util.BCryptHash;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        RoleDAO roleDAO = new RoleDAO(em);
        Role staffRole = roleDAO.getByName(RoleName.STAFF);
        if (staffRole == null) {
            roleDAO.create(Role.builder().name(RoleName.STAFF).build());
        }
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateStaffWithDefaultPasswordAndStaffRole() {
        startServer();

        Integer staffId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "first_name": "Anna",
                          "last_name": "Jensen",
                          "email": "anna.controller@example.com",
                          "phone": "+4512345678",
                          "salary": 25000.00,
                          "working_hours_weekly": 37.5
                        }
                        """)
                .when()
                .post("/staff/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Anna"))
                .body("data.last_name", equalTo("Jensen"))
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
        startServer();

        Integer staffId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "first_name": "Mikkel",
                          "last_name": "Hansen",
                          "email": "mikkel.controller@example.com",
                          "salary": 28000.00,
                          "working_hours_weekly": 30.0
                        }
                        """)
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
                .body("""
                        {
                          "first_name": "Updated Mikkel",
                          "salary": 30000.00,
                          "working_hours_weekly": 37.0
                        }
                        """)
                .when()
                .put("/staff/" + staffId)
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Updated Mikkel"))
                .body("data.salary", equalTo(30000.00F))
                .body("data.working_hours_weekly", equalTo(37.0F))
                .body("data.role", equalTo("STAFF"));

        Staff updatedStaff = staffDAO.getById(staffId);

        assertTrue(originalPasswordHash.equals(updatedStaff.getPasswordHashed()));
        assertTrue(updatedStaff.getRole().getName() == RoleName.STAFF);
        assertTrue(updatedStaff.getSalary().compareTo(new BigDecimal("30000.00")) == 0);
    }

}