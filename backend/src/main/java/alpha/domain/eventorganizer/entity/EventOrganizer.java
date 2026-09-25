package alpha.domain.eventorganizer.entity;

import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "event_organizers", uniqueConstraints = @UniqueConstraint(columnNames = {"event_request_id", "member_id"}))
public class EventOrganizer {

    // Attributes

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //     id | event_request_id | member_id | created_at
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ______ | PERSIST | ______________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String EVENT_REQUEST = "eventRequest";
        public static final String MEMBER = "member";
        public static final String CREATED_AT = "createdAt";
    }

}