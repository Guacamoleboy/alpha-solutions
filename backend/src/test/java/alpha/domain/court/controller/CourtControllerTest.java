package alpha.domain.court.controller;

import alpha.ATest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;

class CourtControllerTest extends ATest {

    // Attributes

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupCourtController() {
        startServer();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateAndUpdateCourtRequiredMembership() {
        Integer firstMembershipId = createMembership("Court Basic " + UUID.randomUUID());
        Integer secondMembershipId = createMembership("Court Premium " + UUID.randomUUID());

        Integer courtId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "name": "%s",
                          "active": true,
                          "required_membership_id": %d
                        }
                        """.formatted("Test Court " + UUID.randomUUID(), firstMembershipId))
                .when()
                .post("/court/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.required_membership_id", equalTo(firstMembershipId))
                .extract()
                .path("data.id");

        RestAssured
                .given()
                .contentType("application/json")
                .body("{\"required_membership_id\":%d}".formatted(secondMembershipId))
                .when()
                .put("/court/" + courtId)
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.required_membership_id", equalTo(secondMembershipId));
    }

    // _________________________________________________________________________________________________________________

    private Integer createMembership(String name) {
        return RestAssured
                .given()
                .contentType("application/json")
                .body("{\"name\":\"%s\",\"active\":true}".formatted(name))
                .when()
                .post("/membership/")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");
    }

}