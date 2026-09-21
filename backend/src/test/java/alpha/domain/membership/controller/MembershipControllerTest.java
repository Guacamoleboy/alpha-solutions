package alpha.domain.membership.controller;

import alpha.ATest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;

class MembershipControllerTest extends ATest {

    // Attributes

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupMembershipController() {
        startServer();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateAndUpdateMembershipThroughController() {
        Integer membershipId = RestAssured
                .given()
                .contentType("application/json")
                .body("{\"name\":\"Test Membership %s\",\"active\":true}".formatted(UUID.randomUUID()))
                .when()
                .post("/membership/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.active", equalTo(true))
                .extract()
                .path("data.id");

        RestAssured
                .given()
                .contentType("application/json")
                .body("{\"description\":\"Updated description\",\"active\":false}")
                .when()
                .put("/membership/" + membershipId)
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.description", equalTo("Updated description"))
                .body("data.active", equalTo(false));
    }

}