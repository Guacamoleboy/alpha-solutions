package alpha.domain.eventrequest.controller;

import alpha.ATest;
import alpha.testdata.domain.EventTestData;
import alpha.domain.member.entity.Member;
import alpha.domain.role.enums.RoleName;
import alpha.security.jwt.JwtService;
import alpha.testdata.TestJsonData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

class EventWorkflowControllerAuthorizationTest extends ATest {

    // Attributes
    private String memberToken;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupControllerAuthorization() {
        startServer();
        Member member = new EventTestData(em).createMember(RoleName.MEMBER, true);
        memberToken = JwtService.generateAccessToken(member);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRestrictEventRequestUpdatesToOwners() {
        RestAssured.given()
                .auth().oauth2(memberToken)
                .contentType("application/json")
                .body(TestJsonData.body("event-workflow.json", "empty", Map.of()))
                .put("/event-requests/1")
                .then()
                .statusCode(403);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRestrictEventCourtReservationsToOwners() {
        RestAssured.given()
                .auth().oauth2(memberToken)
                .contentType("application/json")
                .body(TestJsonData.body("event-workflow.json", "empty", Map.of()))
                .post("/event-court-reservations/")
                .then()
                .statusCode(403);

        RestAssured.given()
                .auth().oauth2(memberToken)
                .delete("/event-court-reservations/1")
                .then()
                .statusCode(403);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRestrictEventOrganizerChangesToOwners() {
        RestAssured.given()
                .auth().oauth2(memberToken)
                .contentType("application/json")
                .body(TestJsonData.body("event-workflow.json", "empty", Map.of()))
                .post("/event-organizers/")
                .then()
                .statusCode(403);

        RestAssured.given()
                .auth().oauth2(memberToken)
                .delete("/event-organizers/1")
                .then()
                .statusCode(403);
    }

}
