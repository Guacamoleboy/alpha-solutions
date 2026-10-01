package alpha.domain.courtclosure.entity;

import alpha.domain.court.entity.Court;
import alpha.domain.courtclosure.enums.CourtClosureReason;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "court_closures")
public class CourtClosure {

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //     id | court_id | start_time | end_time | reason
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private CourtClosureReason reason;

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String COURT = "court";
        public static final String START_TIME = "startTime";
        public static final String END_TIME = "endTime";
        public static final String REASON = "reason";
    }

}