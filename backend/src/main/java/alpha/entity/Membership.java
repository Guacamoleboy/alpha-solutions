package alpha.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "memberships")
public class Membership {

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //
    //     PgAdmin
    //     _______
    //     id | name | description | price | currency | duration | start_date | end_date | guest_pass | active
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

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "currency")
    private String currency;

    @Column(name = "duration")
    private String duration;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "guest_pass")
    private Boolean guestPass;

    @Column(name = "active")
    private Boolean active;

    // ______ | RELATIONS | ____________________________________________________________________________________________

    // N/A

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String CURRENCY = "currency";
        public static final String DURATION = "duration";
        public static final String START_DATE = "startDate";
        public static final String END_DATE = "endDate";
        public static final String GUEST_PASS = "guestPass";
        public static final String ACTIVE = "active";
    }

}