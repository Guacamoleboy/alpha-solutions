package alpha.domain.eventcourtreservation.dao;

import alpha.ATest;
import alpha.domain.court.entity.Court;
import alpha.domain.eventrequest.EventTestData;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.member.entity.Member;
import alpha.domain.role.enums.RoleName;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventCourtReservationDAOTest extends ATest {

    // Attributes
    private EventCourtReservationDAO reservationDAO;
    private EventTestData testData;
    private Member owner;
    private LocalDateTime start;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupReservationDAO() {
        reservationDAO = new EventCourtReservationDAO(em);
        testData = new EventTestData(em);
        owner = testData.createMember(RoleName.OWNER, false);
        start = testData.futureStart();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindOnlyOverlappingReservationsForConfirmedEvents() {
        Court reservedCourt = testData.createCourt();
        EventRequest confirmedEvent = testData.createEventRequest(
                owner,
                EventRequestStatus.ACCEPTED,
                start,
                start.plusHours(2)
        );
        testData.createReservation(confirmedEvent, reservedCourt);

        Court deniedEventCourt = testData.createCourt();
        EventRequest deniedEvent = testData.createEventRequest(
                owner,
                EventRequestStatus.DENIED,
                start,
                start.plusHours(2)
        );
        testData.createReservation(deniedEvent, deniedEventCourt);

        assertTrue(reservationDAO.existsAcceptedReservationOverlap(
                reservedCourt.getId(), start.plusHours(1), start.plusHours(3)));
        assertFalse(reservationDAO.existsAcceptedReservationOverlap(
                reservedCourt.getId(), start.plusHours(2), start.plusHours(3)));
        assertFalse(reservationDAO.existsAcceptedReservationOverlap(
                deniedEventCourt.getId(), start, start.plusHours(1)));
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldExcludeCurrentEventButFindOtherConfirmedEventOverlap() {
        Court court = testData.createCourt();
        EventRequest reservedEvent = testData.createEventRequest(
                owner,
                EventRequestStatus.ACCEPTED,
                start,
                start.plusHours(2)
        );
        testData.createReservation(reservedEvent, court);
        EventRequest otherEvent = testData.createEventRequest(
                owner,
                EventRequestStatus.ACCEPTED,
                start.plusHours(1),
                start.plusHours(3)
        );

        assertTrue(reservationDAO.existsOtherAcceptedEventOverlap(
                court.getId(), otherEvent.getId(), start.plusHours(1), start.plusHours(2)));
        assertFalse(reservationDAO.existsOtherAcceptedEventOverlap(
                court.getId(), reservedEvent.getId(), start, start.plusHours(2)));
    }

}