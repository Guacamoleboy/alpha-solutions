package alpha.testdata;

import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.enums.BookingStatus;
import alpha.domain.court.dao.CourtDAO;
import alpha.domain.court.entity.Court;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.domain.membership.entity.Membership;
import alpha.domain.role.dao.RoleDAO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.domain.settings.operatinghour.dao.OperatingHourDAO;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.testdata.domain.BookingProfile;
import alpha.testdata.domain.CourtProfile;
import alpha.testdata.domain.MemberProfile;
import alpha.testdata.domain.OperatingHourProfile;
import alpha.util.BCryptHash;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public final class TestDataFactory {

    // Attributes
    private final EntityManager entityManager;

    // _________________________________________________________________________________________________________________

    public TestDataFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // _________________________________________________________________________________________________________________

    public Role role(RoleName name) {
        RoleDAO dao = new RoleDAO(entityManager);
        Role role = dao.getByName(name);
        return role == null ? dao.create(Role.builder().name(name).build()) : role;
    }

    // _________________________________________________________________________________________________________________

    public Member member(MemberProfile sample) {
        return member(sample, null, null);
    }

    // _________________________________________________________________________________________________________________

    public Member member(MemberProfile sample, RoleName roleOverride, Membership membership) {
        return new MemberDAO(entityManager).create(newMember(sample, roleOverride, membership));
    }

    // _________________________________________________________________________________________________________________

    public Member newMember(MemberProfile sample) {
        return newMember(sample, null, null);
    }

    // _________________________________________________________________________________________________________________

    public Member newMember(MemberProfile sample, RoleName roleOverride, Membership membership) {
        JsonNode data = TestJsonData.sample("member.json", sample.getKey());
        String password = optionalText(data, "password");
        String passwordHashed = optionalText(data, "passwordHashed");
        if (passwordHashed == null && password != null) {
            passwordHashed = BCryptHash.hash(password);
        }

        RoleName configuredRole = RoleName.valueOf(data.path("role").asText());
        return Member.builder()
                .firstName(data.path("firstName").asText())
                .lastName(data.path("lastName").asText())
                .email(data.path("emailPrefix").asText() + "." + UUID.randomUUID() + "@testmail.dk")
                .passwordHashed(passwordHashed)
                .phone(optionalText(data, "phone"))
                .dateOfBirth(optionalDate(data, "dateOfBirth"))
                .gender(optionalText(data, "gender"))
                .lastPlayed(optionalDate(data, "lastPlayed"))
                .role(role(roleOverride == null ? configuredRole : roleOverride))
                .membership(membership)
                .build();
    }

    // _________________________________________________________________________________________________________________

    public String password(MemberProfile sample) {
        return TestJsonData.sample("member.json", sample.getKey()).path("password").asText();
    }

    // _________________________________________________________________________________________________________________

    public String wrongPassword(MemberProfile sample) {
        return TestJsonData.sample("member.json", sample.getKey()).path("wrongPassword").asText();
    }

    // _________________________________________________________________________________________________________________

    private static String optionalText(JsonNode data, String field) {
        JsonNode value = data.path(field);
        return value.isMissingNode() || value.isNull() ? null : value.asText();
    }

    // _________________________________________________________________________________________________________________

    private static LocalDate optionalDate(JsonNode data, String field) {
        String value = optionalText(data, field);
        return value == null ? null : LocalDate.parse(value);
    }

    // _________________________________________________________________________________________________________________

    public Court court() {
        return court(CourtProfile.ACTIVE);
    }

    // _________________________________________________________________________________________________________________

    public Court court(CourtProfile sample) {
        JsonNode data = TestJsonData.sample("court.json", sample.getKey());
        return new CourtDAO(entityManager).create(Court.builder()
                .name(data.path("namePrefix").asText() + " " + UUID.randomUUID())
                .active(data.path("active").asBoolean())
                .build());
    }

    // _________________________________________________________________________________________________________________

    public Booking booking(Member member, Court court, LocalDateTime start, BookingProfile sample) {
        JsonNode data = TestJsonData.sample("booking.json", sample.getKey());
        return new BookingDAO(entityManager).create(Booking.builder()
                .member(member)
                .court(court)
                .startTime(start)
                .endTime(start.plusMinutes(data.path("durationMinutes").asLong()))
                .status(BookingStatus.valueOf(data.path("status").asText()))
                .build());
    }

    // _________________________________________________________________________________________________________________

    public OperatingHour operatingHour(OperatingHourProfile sample) {
        JsonNode data = TestJsonData.sample("operating-hour.json", sample.getKey());
        return new OperatingHourDAO(entityManager).create(OperatingHour.builder()
                .dayOfWeek(DayOfWeek.valueOf(data.path("dayOfWeek").asText()))
                .openTime(LocalTime.parse(data.path("openTime").asText()))
                .closeTime(LocalTime.parse(data.path("closeTime").asText()))
                .closed(data.path("closed").asBoolean())
                .build());
    }

    // _________________________________________________________________________________________________________________

    public OperatingHour operatingHour(DayOfWeek day, OperatingHourProfile sample) {
        JsonNode data = TestJsonData.sample("operating-hour.json", sample.getKey());
        return new OperatingHourDAO(entityManager).create(OperatingHour.builder()
                .dayOfWeek(day)
                .openTime(LocalTime.parse(data.path("openTime").asText()))
                .closeTime(LocalTime.parse(data.path("closeTime").asText()))
                .closed(data.path("closed").asBoolean())
                .build());
    }

}