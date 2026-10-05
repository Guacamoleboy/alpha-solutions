package alpha.testdata.domain;

import alpha.domain.court.entity.Court;
import alpha.domain.eventcourtreservation.dao.EventCourtReservationDAO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventorganizer.dao.EventOrganizerDAO;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventrequest.dao.EventRequestDAO;
import alpha.domain.eventrequest.dto.request.EventRequestRequestDTO;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.member.entity.Member;
import alpha.domain.membership.dao.MembershipDAO;
import alpha.domain.membership.entity.Membership;
import alpha.domain.role.enums.RoleName;
import alpha.domain.settings.operatinghour.dao.OperatingHourDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.testdata.TestDataFactory;
import alpha.testdata.TestJsonData;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public final class EventTestData {

    // Attributes
    private final EntityManager em;
    private final TestDataFactory testDataFactory;

    // _________________________________________________________________________________________________________________

    public EventTestData(EntityManager em) {
        this.em = em;
        this.testDataFactory = new TestDataFactory(em);
    }

    // _________________________________________________________________________________________________________________

    public Member createMember(RoleName roleName, boolean premium) {
        Membership membership = premium ? createPremiumMembership() : null;
        return testDataFactory.member(MemberProfile.EVENT_MEMBER, roleName, membership);
    }

    // _________________________________________________________________________________________________________________

    public Court createCourt() {
        return testDataFactory.court(CourtProfile.ACTIVE);
    }

    // _________________________________________________________________________________________________________________

    public EventRequest createEventRequest(Member requester, EventRequestStatus status, LocalDateTime start, LocalDateTime end) {
        JsonNode sample = TestJsonData.sample("event-request.json", "standard");
        return new EventRequestDAO(em).create(EventRequest.builder()
                .name(sample.path("name").asText())
                .startTime(start)
                .endTime(end)
                .guestCount(sample.path("guestCount").asInt())
                .requestedCourtCount(sample.path("requestedCourtCount").asInt())
                .equipmentRequired(sample.path("equipmentRequired").asBoolean())
                .organizerEmail(requester.getEmail())
                .status(status)
                .requester(requester)
                .build());
    }

    // _________________________________________________________________________________________________________________

    public EventCourtReservation createReservation(EventRequest eventRequest, Court court) {
        return new EventCourtReservationDAO(em).create(EventCourtReservation.builder()
                .eventRequest(eventRequest)
                .court(court)
                .build());
    }

    // _________________________________________________________________________________________________________________

    public EventOrganizer addOrganizer(EventRequest eventRequest, Member member) {
        return new EventOrganizerDAO(em).create(EventOrganizer.builder()
                .eventRequest(eventRequest).member(member).build());
    }

    // _________________________________________________________________________________________________________________

    public LocalDateTime futureStart() {
        return LocalDate.now().plusDays(14).atTime(10, 0);
    }

    // _________________________________________________________________________________________________________________

    public void configureOperatingHours(LocalDate date) {
        JsonNode sample = TestJsonData.sample("operating-hour.json", OperatingHourProfile.EVENT_OPEN.getKey());
        OperatingHourDAO operatingHourDAO = new OperatingHourDAO(em);
        OperatingHour operatingHour = operatingHourDAO.getAll().stream()
                .filter(hour -> hour.getDayOfWeek() == date.getDayOfWeek())
                .findFirst()
                .orElse(null);
        if (operatingHour == null) {
            operatingHour = OperatingHour.builder()
                    .dayOfWeek(date.getDayOfWeek())
                    .build();
        }
        operatingHour.setOpenTime(LocalTime.parse(sample.path("openTime").asText()));
        operatingHour.setCloseTime(LocalTime.parse(sample.path("closeTime").asText()));
        operatingHour.setClosed(sample.path("closed").asBoolean());
        if (operatingHour.getId() == null) {
            operatingHourDAO.create(operatingHour);
        } else {
            operatingHourDAO.update(operatingHour);
        }
    }

    // _________________________________________________________________________________________________________________

    public EventRequestRequestDTO validEventRequest(String name, LocalDateTime start, LocalDateTime end) {
        JsonNode sample = TestJsonData.sample("event-request.json", "standard");
        EventRequestRequestDTO dto = new EventRequestRequestDTO();
        dto.setName(name);
        dto.setStartTime(start);
        dto.setEndTime(end);
        dto.setGuestCount(sample.path("guestCount").asInt());
        dto.setRequestedCourtCount(sample.path("requestedCourtCount").asInt());
        dto.setEquipmentRequired(sample.path("equipmentRequired").asBoolean());
        return dto;
    }

    // _________________________________________________________________________________________________________________

    private Membership createPremiumMembership() {
        MembershipDAO membershipDAO = new MembershipDAO(em);
        JsonNode sample = TestJsonData.sample("membership.json", "premium");
        Membership existingPremium = membershipDAO.getAll().stream()
                .filter(membership -> membership.getId() >= 3)
                .findFirst()
                .orElse(null);
        if (existingPremium != null) {
            return existingPremium;
        }

        Membership membership = null;
        while (membership == null || membership.getId() < 3) {
            membership = membershipDAO.create(Membership.builder()
                    .name(sample.path("namePrefix").asText() + " " + UUID.randomUUID())
                    .active(sample.path("active").asBoolean())
                    .build());
        }
        return membership;
    }

}