package alpha.domain.resources.staff.entity;

import alpha.domain.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "staff")
public class Staff {

    // Attributes

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //
    //     PgAdmin
    //     _______
    //     id | first_name | last_name | email | phone | password_hashed | salary | working_hours_weekly | role_id | created_at
    //
    // __________________
    // Tested: NO
    // Date: 20/09-2026

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password_hashed", nullable = false)
    private String passwordHashed;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "working_hours_weekly")
    private BigDecimal workingHoursWeekly;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    // ______ | RELATIONS | ____________________________________________________________________________________________

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // ______ | PERSIST | ______________________________________________________________________________________________

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String PASSWORD_HASHED = "passwordHashed";
        public static final String SALARY = "salary";
        public static final String WORKING_HOURS_WEEKLY = "workingHoursWeekly";
        public static final String ROLE = "role";
        public static final String CREATED_AT = "createdAt";
    }

}