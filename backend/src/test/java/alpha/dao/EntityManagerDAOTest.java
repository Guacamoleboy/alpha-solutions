package alpha.dao;

import alpha.ATest;
import alpha.domain.member.entity.Member;
import alpha.testdata.domain.MemberProfile;
import alpha.testdata.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntityManagerDAOTest extends ATest {

    // Attributes
    private EntityManagerDAO<Member> entityManagerDAO;
    private TestDataFactory testData;

    // _________________________________________________________________________________________________________________

    @BeforeEach
    public void setupDAO() {
        this.entityManagerDAO = new EntityManagerDAO<>(em, Member.class);
        entityManagerDAO.deleteAll();

        testData = new TestDataFactory(em);
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldCreateMember() {
        // Arrange
        Member member = testData.newMember(MemberProfile.JONAS);

        // Act
        Member createdMember = entityManagerDAO.create(member);

        // Assert
        assertNotNull(createdMember);
        assertNotNull(createdMember.getId());
        assertEquals(member.getFirstName(), createdMember.getFirstName());
        assertEquals(member.getLastName(), createdMember.getLastName());
        assertEquals(member.getEmail(), createdMember.getEmail());
        assertEquals("hashedPassword", createdMember.getPasswordHashed());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldGetById() {
        // Arrange
        Member member = testData.newMember(MemberProfile.NULL_OPTIONAL_FIELDS);

        entityManagerDAO.create(member);

        // Act
        Member retrievedMember = entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(retrievedMember);
        assertEquals(member.getId(), retrievedMember.getId());
        assertEquals(member.getFirstName(), retrievedMember.getFirstName());
        assertEquals(member.getLastName(), retrievedMember.getLastName());
        assertEquals(member.getEmail(), retrievedMember.getEmail());
        assertNull(retrievedMember.getPhone());
        assertNull(retrievedMember.getDateOfBirth());
        assertNull(retrievedMember.getGender());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldUpdateMember() {
        // Arrange
        Member member = testData.newMember(MemberProfile.DENNIS);

        entityManagerDAO.create(member);

        // Act
        member.setPhone("+4511223344");
        member.setGender("Male");

        Member updatedMember = entityManagerDAO.update(member);

        // Assert
        assertNotNull(updatedMember);
        assertEquals(member.getId(), updatedMember.getId());
        assertEquals(member.getFirstName(), updatedMember.getFirstName());
        assertEquals("+4511223344", updatedMember.getPhone());
        assertEquals("Male", updatedMember.getGender());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldDeleteMember() {
        // Arrange
        Member member = testData.newMember(MemberProfile.JORGEN);

        entityManagerDAO.create(member);

        // Act
        Member deletedMember = entityManagerDAO.delete(member);
        Member retrievedAfterDelete = entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(deletedMember);
        assertNull(retrievedAfterDelete);
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldGetAllMembers() {
        // Arrange
        Member member1 = testData.newMember(MemberProfile.JONAS);
        Member member2 = testData.newMember(MemberProfile.TINE);

        entityManagerDAO.create(member1);
        entityManagerDAO.create(member2);

        // Act
        List<Member> allMembers = entityManagerDAO.getAll();

        // Assert
        assertEquals(2, allMembers.size());
        assertTrue(allMembers.stream().anyMatch(m -> m.getFirstName().equals(member1.getFirstName())));
        assertTrue(allMembers.stream().anyMatch(m -> m.getFirstName().equals(member2.getFirstName())));
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldGetColumnById() {
        // Arrange
        Member member = testData.newMember(MemberProfile.KAMILLA);

        entityManagerDAO.create(member);

        // Act
        String retrievedFirstName = entityManagerDAO.getColumnById(member.getId(), "firstName");
        String retrievedEmail = entityManagerDAO.getColumnById(member.getId(), "email");

        // Assert
        assertNotNull(retrievedFirstName);
        assertNotNull(retrievedEmail);
        assertEquals(member.getFirstName(), retrievedFirstName);
        assertEquals(member.getEmail(), retrievedEmail);
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldDeleteById() {
        // Arrange
        Member member = testData.newMember(MemberProfile.JORGEN);

        entityManagerDAO.create(member);

        // Act
        Member deletedById = entityManagerDAO.deleteById(member.getId());
        Member retrievedAfterDelete = entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(deletedById);
        assertNull(retrievedAfterDelete);
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldUpdateColumnById() {
        // Arrange
        Member member = testData.newMember(MemberProfile.DENNIS);

        entityManagerDAO.create(member);

        // Act
        int updatedCount = entityManagerDAO.updateColumnById(
                member.getId(),
                "firstName",
                "Updated"
        );

        String updatedFirstName = entityManagerDAO.getColumnById(member.getId(), "firstName");

        // Assert
        assertEquals(1, updatedCount);
        assertEquals("Updated", updatedFirstName);
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldFindEntityByColumn() {
        // Arrange
        Member member = testData.newMember(MemberProfile.JONAS);

        entityManagerDAO.create(member);

        // Act
        Member foundMember =
                entityManagerDAO.findEntityByColumn(member.getEmail(), "email");

        // Assert
        assertNotNull(foundMember);
        assertEquals(member.getId(), foundMember.getId());
        assertEquals(member.getFirstName(), foundMember.getFirstName());
        assertEquals(member.getEmail(), foundMember.getEmail());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldReturnTrueIfColumnExists() {
        // Arrange
        Member member = testData.newMember(MemberProfile.TINE);

        entityManagerDAO.create(member);

        // Act
        boolean exists = entityManagerDAO.existByColumn(member.getEmail(), "email");
        boolean notExists = entityManagerDAO.existByColumn("nonexistent@example.com", "email");

        // Assert
        assertTrue(exists);
        assertFalse(notExists);
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldExecuteQuerySupplier() {
        // Arrange
        Member member = testData.newMember(MemberProfile.KAMILLA);

        entityManagerDAO.create(member);

        // Act
        Member result =
                entityManagerDAO.executeQuery(
                        () -> entityManagerDAO.getById(member.getId())
                );

        // Assert
        assertNotNull(result);
        assertEquals(member.getFirstName(), result.getFirstName());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldExecuteQueryRunnable() {
        // Arrange
        Member member = testData.newMember(MemberProfile.JORGEN);

        // Act
        entityManagerDAO.executeQuery(
                () -> entityManagerDAO.create(member)
        );

        Member retrievedMember =
                entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(retrievedMember);
        assertEquals(member.getFirstName(), retrievedMember.getFirstName());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldDeleteAllMembers_bulk() {
        // Arrange
        Member member1 = testData.newMember(MemberProfile.JONAS);
        Member member2 = testData.newMember(MemberProfile.DENNIS);

        entityManagerDAO.create(member1);
        entityManagerDAO.create(member2);

        // Act
        int deletedCount = entityManagerDAO.deleteAll();
        List<Member> remainingMembers = entityManagerDAO.getAll();

        // Assert
        assertEquals(2, deletedCount);
        assertTrue(remainingMembers.isEmpty());
    }

    // _________________________________________________________________________________________________________________

    @Test
    public void shouldDeleteAllMembers_safe() {
        // Arrange
        Member member1 = testData.newMember(MemberProfile.KAMILLA);
        Member member2 = testData.newMember(MemberProfile.TINE);

        entityManagerDAO.create(member1);
        entityManagerDAO.create(member2);

        // Act
        int deletedCount = entityManagerDAO.deleteAllSafe();
        List<Member> remainingMembers = entityManagerDAO.getAll();

        // Assert
        assertEquals(2, deletedCount);
        assertTrue(remainingMembers.isEmpty());
    }

}
