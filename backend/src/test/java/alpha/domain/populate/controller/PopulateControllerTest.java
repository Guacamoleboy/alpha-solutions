package alpha.domain.populate.controller;

import alpha.ATest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PopulateControllerTest extends ATest {

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupPopulateController() {
        startServer();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldPopulateDefaultMemberships() {
        RestAssured
                .given()
                .when()
                .post("/populate/membership")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"));

        RestAssured
                .given()
                .when()
                .post("/populate/membership")
                .then()
                .statusCode(200)
                .body("data", equalTo("Already added"));

        List<Map<String, Object>> memberships = RestAssured
                .given()
                .when()
                .get("/membership/all")
                .then()
                .statusCode(200)
                .body("data", hasSize(4))
                .extract()
                .path("data");

        List<String> names = memberships.stream()
                .map(membership -> (String) membership.get("name"))
                .toList();

        assertTrue(names.containsAll(
                List.of("Free", "Basic", "Premium", "Super Premium")
        ));

    }

}