package alpha.domain.eventrequest.entity;

import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import alpha.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "event_requests")
public class EventRequest {

    // Attributes

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //     id | name | start_time | end_time | guest_count | requested_court_count | equipment_required | event_code | status | requester_id | created_at | updated_at
    //
    // __________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "guest_count", nullable = false)
    private Integer guestCount;

    @Column(name = "requested_court_count", nullable = false)
    private Integer requestedCourtCount;

    @Column(name = "equipment_required", nullable = false)
    private Boolean equipmentRequired;

    @Column(name = "event_code", length = 100)
    private String eventCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventRequestStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ______ | RELATIONS | ____________________________________________________________________________________________

    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private Member requester;

    @OneToMany(mappedBy = "eventRequest")
    private List<EventOrganizer> organizers;

    @OneToMany(mappedBy = "eventRequest")
    private List<EventCourtReservation> courtReservations;

    // ______ | PERSIST | ______________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (status == null) {
            status = EventRequestStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String START_TIME = "startTime";
        public static final String END_TIME = "endTime";
        public static final String GUEST_COUNT = "guestCount";
        public static final String REQUESTED_COURT_COUNT = "requestedCourtCount";
        public static final String EQUIPMENT_REQUIRED = "equipmentRequired";
        public static final String EVENT_CODE = "eventCode";
        public static final String STATUS = "status";
        public static final String REQUESTER = "requester";
        public static final String ORGANIZERS = "organizers";
        public static final String COURT_RESERVATIONS = "courtReservations";
        public static final String CREATED_AT = "createdAt";
        public static final String UPDATED_AT = "updatedAt";
    }

}