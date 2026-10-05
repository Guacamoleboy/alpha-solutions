package alpha.domain.member.controller;

import alpha.ATest;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.role.enums.RoleName;
import alpha.security.jwt.JwtService;
import alpha.util.BCryptHash;
import alpha.testdata.TestDataFactory;
import alpha.testdata.TestJsonData;
import alpha.testdata.domain.MemberProfile;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberControllerTest extends ATest {

    // Attributes
    private MemberDAO memberDAO;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupMemberController() {
        memberDAO = new MemberDAO(em);
        startServer();

        List<String> serverRoles = RestAssured
                .given()
                .get("/role/all")
                .then()
                .statusCode(200)
                .extract()
                .path("data.name");

        if (serverRoles == null || !serverRoles.contains(RoleName.MEMBER.name())) {
            RestAssured
                    .given()
                    .contentType("application/json")
                    .body(TestJsonData.body("role.json", "named", Map.of("name", "MEMBER")))
                    .when()
                    .post("/role/")
                    .then()
                    .statusCode(200);
        }

        new TestDataFactory(em).role(RoleName.MEMBER);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateAndUpdateMemberThroughController() {
        String email = "controller.member." + UUID.randomUUID() + "@example.com";

        Integer memberId = RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.bodyFromSample("member.json", "create", "jonas",
                        Map.of("email", email)))
                .when()
                .post("/member/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Jonas"))
                .body("data.role", equalTo("MEMBER"))
                .extract()
                .path("data.id");

        RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.body("member.json", "update", Map.of("firstName", "Updated Controller")))
                .when()
                .put("/member/" + memberId)
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Updated Controller"));

        em.clear();
        Member updatedMember = memberDAO.getById(memberId);
        assertEquals("Updated Controller", updatedMember.getFirstName());

    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldUpdateMemberPasswordThroughController() {
        TestDataFactory testData = new TestDataFactory(em);
        Member member = testData.member(MemberProfile.PASSWORD_CHANGE);

        RestAssured
                .given()
                .auth().oauth2(JwtService.generateAccessToken(member))
                .contentType("application/json")
                .body(TestJsonData.body("member.json", "passwordUpdate", Map.of(
                        "currentPassword", testData.password(MemberProfile.PASSWORD_CHANGE),
                        "newPassword", "NewPassword123!")))
                .when()
                .post("/member/password")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"));

        em.clear();
        Member updatedMember = memberDAO.getById(member.getId());
        assertTrue(BCryptHash.check("NewPassword123!", updatedMember.getPasswordHashed()));
    }

}
