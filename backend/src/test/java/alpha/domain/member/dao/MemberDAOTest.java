package alpha.domain.member.dao;

import alpha.ATest;
import alpha.domain.member.entity.Member;
import alpha.testdata.domain.MemberProfile;
import alpha.testdata.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MemberDAOTest extends ATest {

    // Attributes
    private MemberDAO memberDAO;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    void setupMemberDAO() {
        memberDAO = new MemberDAO(em);
    }

    // _________________________________________________________________________________________________________________

    @Test
    void shouldRefreshMemberFromDatabase() {
        Member member = new TestDataFactory(em).member(MemberProfile.JONAS);

        memberDAO.updateColumnById(member.getId(), Member.Fields.FIRST_NAME, "Updated");
        member.setFirstName("Stale");

        Member refreshedMember = memberDAO.refresh(member);

        assertNotNull(refreshedMember);
        assertEquals("Updated", refreshedMember.getFirstName());
    }

}
