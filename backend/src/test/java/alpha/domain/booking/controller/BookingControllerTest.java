package alpha.domain.booking.controller;

import alpha.ATest;
import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.booking.entity.Booking;
import alpha.domain.court.dao.CourtDAO;
import alpha.domain.court.entity.Court;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.domain.settings.operatinghour.dao.OperatingHourDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.security.jwt.JwtService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingControllerTest extends ATest {

    // Attributes
    private BookingDAO bookingDAO;
    private Member member;
    private Court court;
    private LocalDateTime bookingStart;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupBookingController() {
        bookingDAO = new BookingDAO(em);
        bookingDAO.deleteAll();
        new OperatingHourDAO(em).deleteAll();

        RoleDAO roleDAO = new RoleDAO(em);
        Role memberRole = roleDAO.getByName(RoleName.MEMBER);
        if (memberRole == null) {
            memberRole = roleDAO.create(Role.builder().name(RoleName.MEMBER).build());
        }

        member = Member.builder()
                .firstName("Booking")
                .lastName("Controller")
                .email("booking.controller." + UUID.randomUUID() + "@example.com")
                .passwordHashed("Password12345!")
                .role(memberRole)
                .build();
        new MemberDAO(em).create(member);

        court = Court.builder()
                .name("Controller Court " + UUID.randomUUID())
                .active(true)
                .build();
        new CourtDAO(em).create(court);

        bookingStart = LocalDateTime.now()
                .plusDays(30)
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        OperatingHour operatingHour = OperatingHour.builder()
                .dayOfWeek(bookingStart.getDayOfWeek())
                .openTime(LocalTime.MIN)
                .closeTime(LocalTime.MAX)
                .closed(false)
                .build();
        new OperatingHourDAO(em).create(operatingHour);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldCreateBookingAndRetrieveMemberBookingsThroughController() {
        startServer();
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

        Booking createdBooking = bookingDAO.getById(bookingId);
        assertEquals(member.getId(), createdBooking.getMember().getId());
    }

}
