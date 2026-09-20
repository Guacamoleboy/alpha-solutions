package alpha.domain.member.dao;

import alpha.ATest;
import alpha.domain.member.entity.Member;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MemberDAOTest extends ATest {

    // Attributes
    private MemberDAO memberDAO;
    private Role memberRole;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupMemberDAO() {
        memberDAO = new MemberDAO(em);
        RoleDAO roleDAO = new RoleDAO(em);
        memberRole = roleDAO.getByName(RoleName.MEMBER);

        if (memberRole == null) {
            memberRole = roleDAO.create(Role.builder().name(RoleName.MEMBER).build());
        }
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRefreshMemberFromDatabase() {
        Member member = Member.builder()
                .firstName("Refresh")
                .lastName("Member")
                .email("refresh.member@example.com")
                .passwordHashed("hashedPassword")
                .role(memberRole)
                .build();
        memberDAO.create(member);

        memberDAO.updateColumnById(member.getId(), Member.Fields.FIRST_NAME, "Updated");
        member.setFirstName("Stale");

        Member refreshedMember = memberDAO.refresh(member);

        assertNotNull(refreshedMember);
        assertEquals("Updated", refreshedMember.getFirstName());
    }

}