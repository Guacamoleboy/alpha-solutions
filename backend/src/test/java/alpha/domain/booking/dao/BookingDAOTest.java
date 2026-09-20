package alpha.domain.booking.dao;

import alpha.ATest;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.enums.BookingStatus;
import alpha.domain.court.dao.CourtDAO;
import alpha.domain.court.entity.Court;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingDAOTest extends ATest {

    // Attributes
    private BookingDAO bookingDAO;
    private Member member;
    private Court court;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupBookingDAO() {
        bookingDAO = new BookingDAO(em);
        bookingDAO.deleteAll();

        RoleDAO roleDAO = new RoleDAO(em);
        Role memberRole = roleDAO.getByName(RoleName.MEMBER);
        if (memberRole == null) {
            memberRole = roleDAO.create(Role.builder().name(RoleName.MEMBER).build());
        }

        member = Member.builder()
                .firstName("Booking")
                .lastName("Member")
                .email("booking." + UUID.randomUUID() + "@example.com")
                .passwordHashed("hashedPassword")
                .role(memberRole)
                .build();
        new MemberDAO(em).create(member);

        court = Court.builder()
                .name("Booking Court " + UUID.randomUUID())
                .active(true)
                .build();
        new CourtDAO(em).create(court);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindOverlappingActiveBooking() {
        LocalDateTime bookingStart = LocalDateTime.of(2030, 1, 10, 10, 0);
        Booking confirmedBooking = Booking.builder()
                .member(member)
                .court(court)
                .startTime(bookingStart)
                .endTime(bookingStart.plusHours(1))
                .status(BookingStatus.CONFIRMED)
                .build();
        bookingDAO.create(confirmedBooking);

        assertTrue(bookingDAO.existsOverlappingBooking(
                court.getId(),
                bookingStart.plusMinutes(30),
                bookingStart.plusHours(2)
        ));
        assertFalse(bookingDAO.existsOverlappingBooking(
                court.getId(),
                bookingStart.plusHours(1),
                bookingStart.plusHours(2)
        ));
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindMemberBookingOnDateButIgnoreCancelledBooking() {
        LocalDateTime bookingStart = LocalDateTime.of(2030, 1, 11, 10, 0);
        Booking confirmedBooking = Booking.builder()
                .member(member)
                .court(court)
                .startTime(bookingStart)
                .endTime(bookingStart.plusHours(1))
                .status(BookingStatus.CONFIRMED)
                .build();
        bookingDAO.create(confirmedBooking);

        assertTrue(bookingDAO.existsBookingForMemberOnDate(member.getId(), bookingStart));
        assertFalse(bookingDAO.existsBookingForMemberOnDate(
                member.getId(),
                bookingStart.plusDays(1)
        ));

        bookingDAO.updateColumnById(confirmedBooking.getId(), Booking.Fields.STATUS, BookingStatus.CANCELLED);

        assertFalse(bookingDAO.existsBookingForMemberOnDate(member.getId(), bookingStart));
    }

}