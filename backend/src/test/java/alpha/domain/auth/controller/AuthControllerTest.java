package alpha.domain.auth.controller;

import alpha.ATest;
import io.restassured.RestAssured;
import alpha.testdata.TestJsonData;
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
                .body(TestJsonData.bodyFromSample("auth.json", "register", "jonas",
                        Map.of("email", email, "password", password)))
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Jonas"))
                .body("data.membership_name", equalTo("Free"))
                .extract()
                .path("data.id");

        String accessToken = RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.body("auth.json", "login", Map.of("email", email, "password", password)))
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
                .body(TestJsonData.body("auth.json", "login", Map.of("email", email, "password", password)))
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
                .body(TestJsonData.body("auth.json", "refresh", Map.of("refreshToken", refreshToken)))
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
                    .body(TestJsonData.body("role.json", "named", Map.of("name", roleName)))
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
                    .body(TestJsonData.body("membership.json", "create", Map.of("name", "Free")))
                    .when()
                    .post("/membership/")
                    .then()
                    .statusCode(200);
        }
    }

}
