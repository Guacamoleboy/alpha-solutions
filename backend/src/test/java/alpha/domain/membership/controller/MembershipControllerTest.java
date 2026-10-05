package alpha.domain.membership.controller;

import alpha.ATest;
import io.restassured.RestAssured;
import alpha.testdata.TestJsonData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.util.Map;
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
                .body(TestJsonData.body("membership.json", "create", Map.of(
                        "name", "Test Membership " + UUID.randomUUID())))
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
                .body(TestJsonData.body("membership.json", "update", Map.of(
                        "description", "Updated description", "active", false)))
                .when()
                .put("/membership/" + membershipId)
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.description", equalTo("Updated description"))
                .body("data.active", equalTo(false));
    }

}
