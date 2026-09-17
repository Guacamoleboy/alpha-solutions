package alpha.domain.booking.entity;

import alpha.domain.booking.enums.BookingStatus;
import alpha.domain.court.entity.Court;
import alpha.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //
    //     PgAdmin
    //     _______
    //     id | member_id | court_id | start_time | end_time | status | created_at
    //
    // __________________
    // Tested: NO
    // Date: 11/09-2026

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ______ | PERSIST | ______________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = BookingStatus.CONFIRMED;
        }
    }

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String MEMBER = "member";
        public static final String COURT = "court";
        public static final String START_TIME = "startTime";
        public static final String END_TIME = "endTime";
        public static final String STATUS = "status";
        public static final String CREATED_AT = "createdAt";
    }

}