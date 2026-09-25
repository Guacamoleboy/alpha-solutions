package alpha.domain.eventcourtreservation.entity;

import alpha.domain.court.entity.Court;
import alpha.domain.eventrequest.entity.EventRequest;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "event_court_reservations", uniqueConstraints = @UniqueConstraint(columnNames = {"event_request_id", "court_id"}))
public class EventCourtReservation {

    // Attributes

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //     id | event_request_id | court_id | created_at
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ______ | RELATIONS | ____________________________________________________________________________________________

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_request_id", nullable = false)
    private EventRequest eventRequest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    // ______ | PERSIST | ______________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String EVENT_REQUEST = "eventRequest";
        public static final String COURT = "court";
        public static final String CREATED_AT = "createdAt";
    }

}