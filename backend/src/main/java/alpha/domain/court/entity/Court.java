package alpha.domain.court.entity;

import alpha.domain.court.enums.CourtSurface;
import alpha.domain.membership.entity.Membership;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "courts")
public class Court {

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //
    //     PgAdmin
    //     _______
    //     id | name | active | created_at | surface | latitude | longitude | orientation_degrees | elevation
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

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "surface")
    private CourtSurface surface;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "orientation_degrees", precision = 6, scale = 2)
    private BigDecimal orientationDegrees;

    @Column(name = "elevation")
    private BigDecimal elevation;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    // ______ | RELATIONS | ____________________________________________________________________________________________

    @ManyToOne
    @JoinColumn(name = "required_membership_id")
    private Membership requiredMembership;

    @OneToMany(mappedBy = "court")
    private List<EventCourtReservation> eventCourtReservations;

    // ______ | PERSIST | ______________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ACTIVE = "active";
        public static final String SURFACE = "surface";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ORIENTATION_DEGREES = "orientationDegrees";
        public static final String ELEVATION = "elevation";
        public static final String CREATED_AT = "createdAt";
        public static final String REQUIRED_MEMBERSHIP = "requiredMembership";
        public static final String EVENT_COURT_RESERVATIONS = "eventCourtReservations";
    }

}