package alpha.domain.member.controller;

import alpha.ATest;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.security.jwt.JwtService;
import alpha.util.BCryptHash;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberControllerTest extends ATest {

    // Attributes
    private MemberDAO memberDAO;
    private Role memberRole;

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
                    .body("{\"name\":\"MEMBER\"}")
                    .when()
                    .post("/role/")
                    .then()
                    .statusCode(200);
        }

        RoleDAO roleDAO = new RoleDAO(em);
        memberRole = roleDAO.getByName(RoleName.MEMBER);
        if (memberRole == null) {
            memberRole = roleDAO.create(Role.builder().name(RoleName.MEMBER).build());
        }
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateAndUpdateMemberThroughController() {
        String email = "controller.member." + UUID.randomUUID() + "@example.com";

        Integer memberId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "firstName": "Controller",
                          "lastName": "Member",
                          "email": "%s",
                          "passwordHashed": "Password12345!"
                        }
                        """.formatted(email))
                .when()
                .post("/member/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.first_name", equalTo("Controller"))
                .body("data.role", equalTo("MEMBER"))
                .extract()
                .path("data.id");

        RestAssured
                .given()
                .contentType("application/json")
                .body("{\"first_name\":\"Updated Controller\"}")
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
        Member member = Member.builder()
                .firstName("Password")
                .lastName("Member")
                .email("password.member." + UUID.randomUUID() + "@example.com")
                .passwordHashed(BCryptHash.hash("OldPassword123!"))
                .role(memberRole)
                .build();
        memberDAO.create(member);

        RestAssured
                .given()
                .auth().oauth2(JwtService.generateAccessToken(member))
                .contentType("application/json")
                .body("""
                        {
                          "current_password": "OldPassword123!",
                          "new_password": "NewPassword123!",
                          "confirm_password": "NewPassword123!"
                        }
                        """)
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
