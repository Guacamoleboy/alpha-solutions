package alpha.domain.booking.controller;

import alpha.ATest;
import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.court.entity.Court;
import alpha.testdata.domain.EventTestData;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.member.entity.Member;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.court.dao.CourtDAO;
import alpha.domain.settings.operatinghour.dao.OperatingHourDAO;
import alpha.security.jwt.JwtService;
import alpha.testdata.TestJsonData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Map;
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
        clearBookingProfiles();
        startServer();
        setBookingStart();
        ensureMemberRole();
        createMember();
        createCourt();
        createOperatingHour();
    }

    // _________________________________________________________________________________________________________________

    private void clearBookingProfiles() {
        bookingDAO = new BookingDAO(em);
        bookingDAO.deleteAll();
        new OperatingHourDAO(em).deleteAll();
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
                    .body(TestJsonData.body("role.json", "named", Map.of("name", "MEMBER")))
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
                .body(TestJsonData.bodyFromSample("member.json", "create", "dennis", Map.of(
                        "email", "booking.controller." + UUID.randomUUID() + "@example.com")))
                .when()
                .post("/member/")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        member = new MemberDAO(em).getById(memberId);
    }

    // _________________________________________________________________________________________________________________

    private void createCourt() {
        Integer courtId = RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.body("court.json", "create", Map.of(
                        "name", "Controller Court " + UUID.randomUUID())))
                .when()
                .post("/court/")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        court = new CourtDAO(em).getById(courtId);
    }

    // _________________________________________________________________________________________________________________

    private void createOperatingHour() {
        RestAssured
                .given()
                .contentType("application/json")
                .body(TestJsonData.body("operating-hour.json", "bookingController", Map.of(
                        "dayOfWeek", bookingStart.getDayOfWeek().toString())))
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
                .body(bookingRequestBody())
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

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRejectBookingOnCourtReservedForConfirmedEvent() {
        EventTestData testData = new EventTestData(em);
        Member owner = testData.createMember(RoleName.OWNER, false);
        var eventRequest = testData.createEventRequest(
                owner,
                EventRequestStatus.ACCEPTED,
                bookingStart,
                bookingStart.plusHours(2)
        );
        testData.createReservation(eventRequest, court);

        String accessToken = JwtService.generateAccessToken(member);

        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(bookingRequestBody())
                .when()
                .post("/booking/")
                .then()
                .statusCode(409);
    }

    private String bookingRequestBody() {
        return TestJsonData.body("booking.json", "create", Map.of(
                "courtId", court.getId(), "startTime", bookingStart.toString(),
                "endTime", bookingStart.plusHours(1).toString()));
    }

}
