package alpha.domain.eventrequest.dao;

import alpha.ATest;
import alpha.domain.eventorganizer.dao.EventOrganizerDAO;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventrequest.EventTestData;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.member.entity.Member;
import alpha.domain.role.enums.RoleName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventRequestDAOTest extends ATest {

    // Attributes
    private EventRequestDAO eventRequestDAO;
    private EventTestData testData;
    private LocalDateTime eventStart;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupEventRequestDAO() {
        eventRequestDAO = new EventRequestDAO(em);
        testData = new EventTestData(em);
        eventStart = testData.futureStart();
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldFindRequestsForRequesterAndCoOrganizerWithoutDuplicates() {
        Member member = testData.createMember(RoleName.MEMBER, true);
        EventRequest requestedEvent = testData.createEventRequest(
                member, EventRequestStatus.PENDING, eventStart, eventStart.plusHours(1));
        new EventOrganizerDAO(em).create(EventOrganizer.builder()
                .eventRequest(requestedEvent)
                .member(member)
                .build());

        Member otherRequester = testData.createMember(RoleName.OWNER, false);
        EventRequest organizedEvent = testData.createEventRequest(
                otherRequester, EventRequestStatus.ACCEPTED, eventStart.plusHours(2), eventStart.plusHours(3));
        new EventOrganizerDAO(em).create(EventOrganizer.builder()
                .eventRequest(organizedEvent)
                .member(member)
                .build());

        List<EventRequest> memberEvents = eventRequestDAO.findAllForMember(member.getId());

        assertEquals(2, memberEvents.size());
        assertTrue(eventRequestDAO.existsForMemberOnDate(member.getId(), eventStart.toLocalDate()));
        assertFalse(eventRequestDAO.existsForMemberOnDate(member.getId(), eventStart.toLocalDate().plusDays(1)));
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldReturnNoEventsForMemberWithoutRequestsOrOrganizerAssignments() {
        Member member = testData.createMember(RoleName.MEMBER, true);

        assertTrue(eventRequestDAO.findAllForMember(member.getId()).isEmpty());
        assertFalse(eventRequestDAO.existsForMemberOnDate(member.getId(), LocalDate.now().plusDays(14)));
    }

}