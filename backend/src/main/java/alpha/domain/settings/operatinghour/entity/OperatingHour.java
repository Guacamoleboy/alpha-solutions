package alpha.domain.settings.operatinghour.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "operating_hours", uniqueConstraints = {@UniqueConstraint(columnNames = "day_of_week")})
public class OperatingHour {

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //
    //     PgAdmin
    //     _______
    //     id | day_of_week | open_time | close_time | closed
    //
    // __________________
    // Tested: NO
    // Date: 17/09-2026

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "closed", nullable = false)
    private Boolean closed;

    // _________________________________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String DAY_OF_WEEK = "dayOfWeek";
        public static final String OPEN_TIME = "openTime";
        public static final String CLOSE_TIME = "closeTime";
        public static final String CLOSED = "closed";
    }

}