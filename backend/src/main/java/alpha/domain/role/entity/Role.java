package alpha.domain.role.entity;

import alpha.domain.role.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    // _________________________________________________________________________________________________________________

    // Expected Column Layout in DB
    // __________________
    //
    //
    //     PgAdmin
    //     _______
    //     id | name
    //
    // __________________
    // Tested: NO
    // Date: 18/09-2026

    // _________________________________________________________________________________________________________________

    // ______ | COLUMNS | ______________________________________________________________________________________________

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private RoleName name;

    // ______ | NESTED FIELDS | ________________________________________________________________________________________

    public static class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
    }

}