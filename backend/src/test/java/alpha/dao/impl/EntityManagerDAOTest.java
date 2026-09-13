package alpha.dao.impl;

import alpha.ATest;
import alpha.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntityManagerDAOTest extends ATest {

    // Attributes
    private EntityManagerDAO<Member> entityManagerDAO;

    // ____________________________________________________

    @BeforeEach
    public void setupDAO() {
        this.entityManagerDAO = new EntityManagerDAO<>(em, Member.class);
        entityManagerDAO.deleteAll();
    }

    // ____________________________________________________

    @Test
    public void shouldCreateMember() {
        // Arrange
        Member member = new Member();
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setEmail("john@example.com");
        member.setPasswordHashed("hashedPassword");
        member.setPhone("+4512345678");
        member.setDateOfBirth(LocalDate.of(1990, 5, 15));
        member.setGender("Male");
        member.setLastPlayed(LocalDate.of(2026, 9, 11));

        // Act
        Member createdMember = entityManagerDAO.create(member);

        // Assert
        assertNotNull(createdMember);
        assertNotNull(createdMember.getId());
        assertEquals("John", createdMember.getFirstName());
        assertEquals("Doe", createdMember.getLastName());
        assertEquals("john@example.com", createdMember.getEmail());
        assertEquals("hashedPassword", createdMember.getPasswordHashed());
    }

    // ____________________________________________________

    @Test
    public void shouldGetById() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setEmail("jane@example.com");
        member.setPasswordHashed("hashedPassword");
        member.setPhone("+4598765432");
        member.setDateOfBirth(LocalDate.of(1995, 8, 20));
        member.setGender("Female");

        entityManagerDAO.create(member);

        // Act
        Member retrievedMember = entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(retrievedMember);
        assertEquals(member.getId(), retrievedMember.getId());
        assertEquals("Jane", retrievedMember.getFirstName());
        assertEquals("Doe", retrievedMember.getLastName());
        assertEquals("jane@example.com", retrievedMember.getEmail());
    }

    // ____________________________________________________

    @Test
    public void shouldUpdateMember() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Michael");
        member.setLastName("Smith");
        member.setEmail("michael@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        member.setPhone("+4511223344");
        member.setGender("Male");

        Member updatedMember = entityManagerDAO.update(member);

        // Assert
        assertNotNull(updatedMember);
        assertEquals(member.getId(), updatedMember.getId());
        assertEquals("Michael", updatedMember.getFirstName());
        assertEquals("+4511223344", updatedMember.getPhone());
        assertEquals("Male", updatedMember.getGender());
    }

    // ____________________________________________________

    @Test
    public void shouldDeleteMember() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Guest");
        member.setLastName("User");
        member.setEmail("guest@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        Member deletedMember = entityManagerDAO.delete(member);
        Member retrievedAfterDelete = entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(deletedMember);
        assertNull(retrievedAfterDelete);
    }

    // ____________________________________________________

    @Test
    public void shouldGetAllMembers() {
        // Arrange
        Member member1 = new Member();
        member1.setFirstName("John");
        member1.setLastName("Doe");
        member1.setEmail("john@example.com");
        member1.setPasswordHashed("password1");

        Member member2 = new Member();
        member2.setFirstName("Jane");
        member2.setLastName("Doe");
        member2.setEmail("jane@example.com");
        member2.setPasswordHashed("password2");

        entityManagerDAO.create(member1);
        entityManagerDAO.create(member2);

        // Act
        List<Member> allMembers = entityManagerDAO.getAll();

        // Assert
        assertEquals(2, allMembers.size());
        assertTrue(allMembers.stream().anyMatch(m -> m.getFirstName().equals("John")));
        assertTrue(allMembers.stream().anyMatch(m -> m.getFirstName().equals("Jane")));
    }

    // ____________________________________________________

    @Test
    public void shouldGetColumnById() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Column");
        member.setLastName("Test");
        member.setEmail("column@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        String retrievedFirstName = entityManagerDAO.getColumnById(member.getId(), "firstName");
        String retrievedEmail = entityManagerDAO.getColumnById(member.getId(), "email");

        // Assert
        assertNotNull(retrievedFirstName);
        assertNotNull(retrievedEmail);
        assertEquals("Column", retrievedFirstName);
        assertEquals("column@example.com", retrievedEmail);
    }

    // ____________________________________________________

    @Test
    public void shouldDeleteById() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Temp");
        member.setLastName("Member");
        member.setEmail("temp@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        Member deletedById = entityManagerDAO.deleteById(member.getId());
        Member retrievedAfterDelete = entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(deletedById);
        assertNull(retrievedAfterDelete);
    }

    // ____________________________________________________

    @Test
    public void shouldUpdateColumnById() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Initial");
        member.setLastName("Member");
        member.setEmail("initial@example.com");
        member.setPasswordHashed("hashedPassword");

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

    // ____________________________________________________

    @Test
    public void shouldFindEntityByColumn() {
        // Arrange
        Member member = new Member();
        member.setFirstName("FindMe");
        member.setLastName("Member");
        member.setEmail("findme@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        Member foundMember =
                entityManagerDAO.findEntityByColumn("findme@example.com", "email");

        // Assert
        assertNotNull(foundMember);
        assertEquals(member.getId(), foundMember.getId());
        assertEquals("FindMe", foundMember.getFirstName());
        assertEquals("findme@example.com", foundMember.getEmail());
    }

    // ____________________________________________________

    @Test
    public void shouldReturnTrueIfColumnExists() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Exists");
        member.setLastName("Member");
        member.setEmail("exists@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        boolean exists = entityManagerDAO.existByColumn("exists@example.com", "email");
        boolean notExists = entityManagerDAO.existByColumn("nonexistent@example.com", "email");

        // Assert
        assertTrue(exists);
        assertFalse(notExists);
    }

    // ____________________________________________________

    @Test
    public void shouldExecuteQuerySupplier() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Tester");
        member.setLastName("Member");
        member.setEmail("tester@example.com");
        member.setPasswordHashed("hashedPassword");

        entityManagerDAO.create(member);

        // Act
        Member result =
                entityManagerDAO.executeQuery(
                        () -> entityManagerDAO.getById(member.getId())
                );

        // Assert
        assertNotNull(result);
        assertEquals("Tester", result.getFirstName());
    }

    // ____________________________________________________

    @Test
    public void shouldExecuteQueryRunnable() {
        // Arrange
        Member member = new Member();
        member.setFirstName("Runnable");
        member.setLastName("Member");
        member.setEmail("runnable@example.com");
        member.setPasswordHashed("hashedPassword");

        // Act
        entityManagerDAO.executeQuery(
                () -> entityManagerDAO.create(member)
        );

        Member retrievedMember =
                entityManagerDAO.getById(member.getId());

        // Assert
        assertNotNull(retrievedMember);
        assertEquals("Runnable", retrievedMember.getFirstName());
    }

    // ____________________________________________________

    @Test
    public void shouldDeleteAllMembers_bulk() {
        // Arrange
        Member member1 = new Member();
        member1.setFirstName("Bulk1");
        member1.setLastName("Member");
        member1.setEmail("bulk1@example.com");
        member1.setPasswordHashed("password1");

        Member member2 = new Member();
        member2.setFirstName("Bulk2");
        member2.setLastName("Member");
        member2.setEmail("bulk2@example.com");
        member2.setPasswordHashed("password2");

        entityManagerDAO.create(member1);
        entityManagerDAO.create(member2);

        // Act
        int deletedCount = entityManagerDAO.deleteAll();
        List<Member> remainingMembers = entityManagerDAO.getAll();

        // Assert
        assertEquals(2, deletedCount);
        assertTrue(remainingMembers.isEmpty());
    }

    // ____________________________________________________

    @Test
    public void shouldDeleteAllMembers_safe() {
        // Arrange
        Member member1 = new Member();
        member1.setFirstName("Safe1");
        member1.setLastName("Member");
        member1.setEmail("safe1@example.com");
        member1.setPasswordHashed("password1");

        Member member2 = new Member();
        member2.setFirstName("Safe2");
        member2.setLastName("Member");
        member2.setEmail("safe2@example.com");
        member2.setPasswordHashed("password2");

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