package alpha.domain.auth.controller;

import alpha.ATest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class AuthControllerTest extends ATest {

    // Attributes

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupAuthController() {
        startServer();
        ensureRole("MEMBER");
        ensureFreeMembership();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRegisterLoginRefreshAndGetCurrentMember() {
        String email = "auth.controller." + UUID.randomUUID() + "@example.com";
        String password = "Password123!";

        Integer memberId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "first_name": "Auth",
                          "last_name": "Controller",
                          "email": "%s",
                          "password": "%s"
                        }
                        """.formatted(email, password))
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Auth"))
                .body("data.membership_name", equalTo("Free"))
                .extract()
                .path("data.id");

        String accessToken = RestAssured
                .given()
                .contentType("application/json")
                .body("{\"email\":\"%s\",\"password\":\"%s\"}".formatted(email, password))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.access_token", notNullValue())
                .body("data.refresh_token", notNullValue())
                .extract()
                .path("data.access_token");

        String refreshToken = RestAssured
                .given()
                .contentType("application/json")
                .body("{\"email\":\"%s\",\"password\":\"%s\"}".formatted(email, password))
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("data.refresh_token");

        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .when()
                .get("/auth/me")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.id", equalTo(memberId))
                .body("data.email", equalTo(email));

        RestAssured
                .given()
                .contentType("application/json")
                .body("{\"refresh_token\":\"%s\"}".formatted(refreshToken))
                .when()
                .post("/auth/refresh")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.access_token", notNullValue());
    }

    // _________________________________________________________________________________________________________________

    private void ensureRole(String roleName) {
        List<Map<String, Object>> roles = RestAssured
                .given()
                .when()
                .get("/role/all")
                .then()
                .statusCode(200)
                .extract()
                .path("data");

        boolean exists = roles != null && roles.stream()
                .anyMatch(role -> roleName.equals(role.get("name")));
        if (!exists) {
            RestAssured
                    .given()
                    .contentType("application/json")
                    .body("{\"name\":\"%s\"}".formatted(roleName))
                    .when()
                    .post("/role/")
                    .then()
                    .statusCode(200);
        }
    }

    // _________________________________________________________________________________________________________________

    private void ensureFreeMembership() {
        List<Map<String, Object>> memberships = RestAssured
                .given()
                .when()
                .get("/membership/all")
                .then()
                .statusCode(200)
                .extract()
                .path("data");

        boolean exists = memberships != null && memberships.stream()
                .anyMatch(membership -> "Free".equals(membership.get("name")));
        if (!exists) {
            RestAssured
                    .given()
                    .contentType("application/json")
                    .body("{\"name\":\"Free\",\"active\":true}")
                    .when()
                    .post("/membership/")
                    .then()
                    .statusCode(200);
        }
    }

}