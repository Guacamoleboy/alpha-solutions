package alpha.domain.booking.dao;

import alpha.ATest;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.enums.BookingStatus;
import alpha.domain.court.entity.Court;
import alpha.domain.member.entity.Member;
import alpha.testdata.domain.BookingProfile;
import alpha.testdata.domain.MemberProfile;
import alpha.testdata.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
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

        TestDataFactory testData = new TestDataFactory(em);
        member = testData.member(MemberProfile.DENNIS);
        court = testData.court();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindOverlappingActiveBooking() {
        LocalDateTime bookingStart = LocalDateTime.of(2030, 1, 10, 10, 0);
        Booking confirmedBooking = new TestDataFactory(em).booking(member, court, bookingStart, BookingProfile.CONFIRMED);

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
        Booking confirmedBooking = new TestDataFactory(em).booking(member, court, bookingStart, BookingProfile.CONFIRMED);

        assertTrue(bookingDAO.existsBookingForMemberOnDate(member.getId(), bookingStart));
        assertFalse(bookingDAO.existsBookingForMemberOnDate(
                member.getId(),
                bookingStart.plusDays(1)
        ));

        bookingDAO.updateColumnById(confirmedBooking.getId(), Booking.Fields.STATUS, BookingStatus.CANCELLED);

        assertFalse(bookingDAO.existsBookingForMemberOnDate(member.getId(), bookingStart));
    }

}
