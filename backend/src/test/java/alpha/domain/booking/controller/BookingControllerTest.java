package alpha.domain.booking.controller;

import alpha.ATest;
import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.court.entity.Court;
import alpha.domain.member.entity.Member;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.security.jwt.JwtService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class BookingControllerTest extends ATest {

    // Attributes
    private BookingDAO bookingDAO;
    private Member member;
    private Court court;
    private LocalDateTime bookingStart;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupBookingController() {
        clearBookings();
        startServer();
        setBookingStart();
        ensureMemberRole();
        createMember();
        createCourt();
        createOperatingHour();
    }

    // _________________________________________________________________________________________________________________

    private void clearBookings() {
        bookingDAO = new BookingDAO(em);
        bookingDAO.deleteAll();
    }

    // _________________________________________________________________________________________________________________

    private void setBookingStart() {
        bookingStart = LocalDateTime.now()
                .plusDays(30)
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    // _________________________________________________________________________________________________________________

    private void ensureMemberRole() {
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
    }

    // _________________________________________________________________________________________________________________

    private void createMember() {
        Integer memberId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "firstName": "Booking",
                          "lastName": "Controller",
                          "email": "%s",
                          "passwordHashed": "Password12345!"
                        }
                        """.formatted("booking.controller." + UUID.randomUUID() + "@example.com"))
                .when()
                .post("/member/")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        member = Member.builder()
                .id(memberId)
                .firstName("Booking")
                .lastName("Controller")
                .role(Role.builder().name(RoleName.MEMBER).build())
                .build();
    }

    // _________________________________________________________________________________________________________________

    private void createCourt() {
        Integer courtId = RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "name": "%s",
                          "active": true
                        }
                        """.formatted("Controller Court " + UUID.randomUUID()))
                .when()
                .post("/court/")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        court = Court.builder()
                .id(courtId)
                .name("Controller Court")
                .active(true)
                .build();
    }

    // _________________________________________________________________________________________________________________

    private void createOperatingHour() {
        RestAssured
                .given()
                .contentType("application/json")
                .body("""
                        {
                          "dayOfWeek": "%s",
                          "openTime": "00:00:00",
                          "closeTime": "23:59:59",
                          "closed": false
                        }
                        """.formatted(bookingStart.getDayOfWeek()))
                .when()
                .post("/settings/operating-hours/")
                .then()
                .statusCode(200);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateBookingAndRetrieveMemberBookingsThroughController() {
        String accessToken = JwtService.generateAccessToken(member);

        Integer bookingId = RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body("""
                        {
                          "court_id": %d,
                          "start_time": "%s",
                          "end_time": "%s"
                        }
                        """.formatted(court.getId(), bookingStart, bookingStart.plusHours(1)))
                .when()
                .post("/booking/")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data.member_id", equalTo(member.getId()))
                .body("data.court_id", equalTo(court.getId()))
                .extract()
                .path("data.id");

        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .when()
                .get("/booking/mine")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("data", hasSize(1))
                .body("data[0].id", equalTo(bookingId));

    }

}