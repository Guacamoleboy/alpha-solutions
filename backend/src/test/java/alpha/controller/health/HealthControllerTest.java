package alpha.controller.health;

import alpha.ATest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthControllerTest extends ATest {

    // Attributes

    // _________________________________________________________________________________________________________________

    @Test
    @DisplayName("Should test Health for REST API")
    public void shouldTestHealth(){
        // Arrange
        startServer();
        // Act + Assert
        RestAssured
                .given()
                .when()
                .get("/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", equalTo("Health OK"));
    }

}