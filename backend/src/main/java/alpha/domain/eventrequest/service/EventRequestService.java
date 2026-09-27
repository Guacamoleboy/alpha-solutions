package alpha.domain.eventrequest.service;

import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventorganizer.service.EventOrganizerService;
import alpha.domain.eventrequest.dto.request.EventRequestRequestDTO;
import alpha.domain.eventrequest.dao.EventRequestDAO;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.court.service.CourtService;
import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.eventcourtreservation.dao.EventCourtReservationDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.member.service.MemberService;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventRequestService extends EntityManagerService<EventRequest> {   

    // Attributes
    private final MemberService memberService;
    private final EventOrganizerService eventOrganizerService;
    private final OperatingHourService operatingHourService;
    private final EventRequestDAO eventRequestDAO;
    private final CourtService courtService;
    private final BookingDAO bookingDAO;
    private final EventCourtReservationDAO eventCourtReservationDAO;

    // _________________________________________________________________________________________________________________

    public EventRequestService(EntityManager em, MemberService memberService, EventOrganizerService eventOrganizerService, OperatingHourService operatingHourService, CourtService courtService) {
        super(new EventRequestDAO(em), EventRequest.class);
        this.eventRequestDAO = (EventRequestDAO) this.entityManagerDAO;
        this.memberService = memberService;
        this.eventOrganizerService = eventOrganizerService;
        this.operatingHourService = operatingHourService;
        this.courtService = courtService;
        this.bookingDAO = new BookingDAO(em);
        this.eventCourtReservationDAO = new EventCourtReservationDAO(em);
    }

    // _________________________________________________________________________________________________________________

    public EventRequest createEventRequest(Integer memberId, EventRequestRequestDTO dto) {

        Member requester = memberService.getById(memberId);
        validateRequester(requester);
        validateRequest(dto);

        List<Member> coOrganizers = resolveCoOrganizers(dto.getOrganizerEmails(), requester);
        boolean isOwner = requester.getRole() != null && requester.getRole().getName() != null
                && "OWNER".equals(requester.getRole().getName().name());
        if (!isOwner) {
            validateMemberEventAvailability(requester, coOrganizers, dto.getStartTime().toLocalDate());
        }

        EventRequest eventRequest = EventRequest.builder()
                .name(dto.getName().trim())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .guestCount(dto.getGuestCount())
                .requestedCourtCount(dto.getRequestedCourtCount())
                .equipmentRequired(Boolean.TRUE.equals(dto.getEquipmentRequired()))
                .eventCode(blankToNull(dto.getEventCode()))
                .organizerEmail(requester.getEmail())
                .requester(requester)
                .status(isOwner
                        ? EventRequestStatus.ACCEPTED
                        : EventRequestStatus.PENDING)
                .build();

        EventRequest created = create(eventRequest);

        for (Member coOrganizer : coOrganizers) {
            eventOrganizerService.create(EventOrganizer.builder()
                    .eventRequest(created)
                    .member(coOrganizer)
                    .build());
        }

        return created;
    }

    // _________________________________________________________________________________________________________________

    public List<EventRequest> getMemberEventRequests(Integer memberId) {
        if (memberService.getById(memberId) == null) {
            throw new ApiException(404, "Member not found");
        }
        return eventRequestDAO.findAllForMember(memberId);
    }

    // _________________________________________________________________________________________________________________

    public EventRequest updateEventRequest(Integer id, EventRequest changes) {
        EventRequest eventRequest = getById(id);
        if (eventRequest == null) {
            throw new ApiException(404, "Event request not found");
        }
        EventRequestRequestDTO validation = new EventRequestRequestDTO();
        validation.setName(changes.getName());
        validation.setStartTime(changes.getStartTime());
        validation.setEndTime(changes.getEndTime());
        validation.setGuestCount(changes.getGuestCount());
        validation.setRequestedCourtCount(changes.getRequestedCourtCount());
        validation.setEquipmentRequired(changes.getEquipmentRequired());
        validation.setEventCode(changes.getEventCode());
        boolean isExistingPastEvent = eventRequest.getStartTime().toLocalDate().isBefore(LocalDate.now())
                && changes.getStartTime() != null
                && changes.getStartTime().toLocalDate().equals(eventRequest.getStartTime().toLocalDate());
        validateRequest(validation, isExistingPastEvent);
        if (changes.getOrganizerEmail() == null || !changes.getOrganizerEmail().contains("@")
                || changes.getOrganizerEmail().length() > 254) {
            throw new ApiException(400, "A valid organizer email is required");
        }

        if (changes.getStatus() == EventRequestStatus.ACCEPTED && eventRequest.getCourtReservations() != null) {
            eventRequest.getCourtReservations().forEach((reservation) -> {
                Integer courtId = reservation.getCourt().getId();
                if (bookingDAO.existsOverlappingBooking(courtId, changes.getStartTime(), changes.getEndTime())
                        || eventCourtReservationDAO.existsOtherAcceptedEventOverlap(courtId, eventRequest.getId(), changes.getStartTime(), changes.getEndTime())) {
                    throw new ApiException(409, "A selected court is unavailable during the updated event time");
                }
            });
        }

        eventRequest.setName(changes.getName().trim());
        eventRequest.setStartTime(changes.getStartTime());
        eventRequest.setEndTime(changes.getEndTime());
        eventRequest.setGuestCount(changes.getGuestCount());
        eventRequest.setRequestedCourtCount(changes.getRequestedCourtCount());
        eventRequest.setEquipmentRequired(Boolean.TRUE.equals(changes.getEquipmentRequired()));
        eventRequest.setEventCode(blankToNull(changes.getEventCode()));
        eventRequest.setOrganizerEmail(changes.getOrganizerEmail().trim());
        if (changes.getStatus() != null) {
            eventRequest.setStatus(changes.getStatus());
        }
        return update(eventRequest);
    }

    // _________________________________________________________________________________________________________________

    private void validateRequester(Member requester) {
        if (requester == null) {
            throw new ApiException(404, "Member not found");
        }
        boolean isOwner = requester.getRole() != null && requester.getRole().getName() != null
                && "OWNER".equals(requester.getRole().getName().name());
        if (!isOwner && (requester.getMembership() == null || requester.getMembership().getId() < 3)) {
            throw new ApiException(403, "Premium membership is required to request an event");
        }
    }

    // _________________________________________________________________________________________________________________

    private void validateRequest(EventRequestRequestDTO dto) {
        validateRequest(dto, false);
    }

    private void validateRequest(EventRequestRequestDTO dto, boolean allowPastEventDate) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            throw new ApiException(400, "Event name is required");
        }
        if (dto.getName().trim().length() > 100) {
            throw new ApiException(400, "Event name cannot exceed 100 characters");
        }
        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new ApiException(400, "Start and end time are required");
        }
        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            throw new ApiException(400, "Start time must be before end time");
        }
        if (!dto.getStartTime().toLocalDate().equals(dto.getEndTime().toLocalDate())) {
            throw new ApiException(400, "Event must start and end on the same day");
        }
        if (!allowPastEventDate && dto.getStartTime().toLocalDate().isBefore(LocalDate.now())) {
            throw new ApiException(400, "Event date cannot be before today");
        }
        validateOperatingHours(dto.getStartTime(), dto.getEndTime());
        if (dto.getGuestCount() == null || dto.getGuestCount() < 1) {
            throw new ApiException(400, "Guest count must be at least 1");
        }
        if (dto.getGuestCount() > 40) {
            throw new ApiException(400, "An event cannot have more than 40 guests");
        }
        if (dto.getRequestedCourtCount() == null || dto.getRequestedCourtCount() < 1) {
            throw new ApiException(400, "At least one court is required");
        }
        long activeCourtCount = courtService.getAll().stream()
                .filter(court -> Boolean.TRUE.equals(court.getActive()))
                .count();
        if (dto.getRequestedCourtCount() > activeCourtCount) {
            throw new ApiException(400, "Requested courts exceed the number of active courts");
        }
        if (dto.getEventCode() != null && dto.getEventCode().length() > 100) {
            throw new ApiException(400, "Event code cannot exceed 100 characters");
        }
        if (dto.getOrganizerEmails() != null && dto.getOrganizerEmails().size() > 3) {
            throw new ApiException(400, "A maximum of 3 co-organizers is allowed");
        }
    }

    // _________________________________________________________________________________________________________________

    private void validateMemberEventAvailability(Member requester, List<Member> coOrganizers, LocalDate date) {
        if (eventRequestDAO.existsForMemberOnDate(requester.getId(), date)
                || coOrganizers.stream().anyMatch(member -> eventRequestDAO.existsForMemberOnDate(member.getId(), date))) {
            throw new ApiException(409, "Du har allerede et event");
        }
    }

    // _________________________________________________________________________________________________________________

    private void validateOperatingHours(LocalDateTime startTime, LocalDateTime endTime) {
        OperatingHour operatingHour = operatingHourService.getByDayOfWeek(startTime.getDayOfWeek());
        if (operatingHour == null || Boolean.TRUE.equals(operatingHour.getClosed())) {
            throw new ApiException(400, "The selected day is closed");
        }
        if (operatingHour.getOpenTime() == null || operatingHour.getCloseTime() == null
                || startTime.toLocalTime().isBefore(operatingHour.getOpenTime())
                || endTime.toLocalTime().isAfter(operatingHour.getCloseTime())) {
            throw new ApiException(400, "Event must be within operating hours");
        }
        if (startTime.getMinute() != 0 || startTime.getSecond() != 0
                || endTime.getMinute() != 0 || endTime.getSecond() != 0) {
            throw new ApiException(400, "Event times must use full hours");
        }
    }

    // _________________________________________________________________________________________________________________

    private List<Member> resolveCoOrganizers(List<String> emails, Member requester) {
        if (emails == null) {
            return List.of();
        }
        return emails.stream()
                .filter(email -> email != null && !email.isBlank())
                .map(String::trim)
                .distinct()
                .map(email -> {
                    Member member = memberService.findEntityByColumn(email, Member.Fields.EMAIL);
                    if (member == null) {
                        throw new ApiException(404, "Co-organizer member not found: " + email);
                    }
                    if (member.getId().equals(requester.getId())) {
                        throw new ApiException(400, "Requester cannot be added as a co-organizer");
                    }
                    return member;
                })
                .toList();
    }

    // _________________________________________________________________________________________________________________

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

}