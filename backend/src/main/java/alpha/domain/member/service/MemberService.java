package alpha.domain.member.service;

import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.dto.request.MemberPasswordRequestDTO;
import alpha.domain.member.dto.request.ForgotPasswordVerifyRequestDTO;
import alpha.domain.member.dto.request.MemberRequestDTO;
import alpha.domain.member.dto.response.MemberResponseDTO;
import alpha.domain.member.entity.Member;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.domain.role.service.RoleService;
import alpha.exception.ApiException;
import alpha.domain.member.mapper.response.MemberResponseMapper;
import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.service.MembershipService;
import alpha.service.EntityManagerService;
import alpha.util.BCryptHash;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;

public class MemberService extends EntityManagerService<Member> {

    // Attributes
    private final MemberDAO memberDAO;
    private final RoleService roleService;
    private final MembershipService membershipService;

    // _________________________________________________________________________________________________________________

    public MemberService(EntityManager em){
        super(new MemberDAO(em), Member.class);
        this.memberDAO = (MemberDAO) this.entityManagerDAO;
        this.roleService = new RoleService(em);
        this.membershipService = new MembershipService(em);
    }

    // _________________________________________________________________________________________________________________

    public Member createMember(Member member) {

        validateNotEmpty(member.getFirstName(), "Member.firstName");
        validateNotEmpty(member.getLastName(), "Member.lastName");
        validateNotEmpty(member.getEmail(), "Member.email");
        validateNotEmpty(member.getPasswordHashed(), "Member.password");

        // Role + Validation
        Role role = roleService.getByName(RoleName.MEMBER);
        if (role == null) {
            throw new ApiException(
                    500, "Default member role not found"
            );
        }

        // Password hashing
        member.setPasswordHashed(BCrypt.hashpw(member.getPasswordHashed(), BCrypt.gensalt()));
        member.setRole(role);

        return super.create(member);
    }

    // _________________________________________________________________________________________________________________

    public boolean existsById(Integer memberId) {
        validateNotEmpty(memberId, "Member.id");
        return memberDAO.existByColumn(
                memberId,
                Member.Fields.ID
        );
    }

    // _________________________________________________________________________________________________________________
    // Not a good method right now. But it works for sprint 0.

    public MemberResponseDTO update(Integer id, MemberRequestDTO dto) {
        Member member = memberDAO.getById(id);

        if (member == null) {
            throw new ApiException(404, "Member not found");
        }

        if (dto.getFirstName() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.FIRST_NAME,
                    dto.getFirstName()
            );
        }
        if (dto.getLastName() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.LAST_NAME,
                    dto.getLastName()
            );
        }
        if (dto.getEmail() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.EMAIL,
                    dto.getEmail()
            );
        }
        if (dto.getPhone() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.PHONE,
                    dto.getPhone()
            );
        }
        if (dto.getDateOfBirth() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.DATE_OF_BIRTH,
                    dto.getDateOfBirth()
            );
        }
        if (dto.getGender() != null) {
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.GENDER,
                    dto.getGender()
            );
        }
        if (dto.getMembershipId() != null) {
            Membership membership = membershipService.getById(dto.getMembershipId());
            if (membership == null) {
                throw new ApiException(404, "Membership not found");
            }
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.MEMBERSHIP,
                    membership
            );
        }
        if (dto.getPassword() != null) {
            validateNewPassword(dto.getPassword(), dto.getPasswordAgain());
            memberDAO.updateColumnById(
                    id,
                    Member.Fields.PASSWORD_HASHED,
                    BCryptHash.hash(dto.getPassword())
            );
        }

        memberDAO.refresh(member);
        return MemberResponseMapper.toDTO(member);
    }

    // _________________________________________________________________________________________________________________

    public void updatePassword(Integer memberId, MemberPasswordRequestDTO dto) {

        // Member check
        Member member = memberDAO.getById(memberId);
        if (member == null) {
            throw new ApiException(404, "Member not found");
        }

        // Validate fields
        validateNotEmpty(dto.getCurrentPassword(), "current_password");
        validateNotEmpty(dto.getNewPassword(), "new_password");
        validateNotEmpty(dto.getConfirmPassword(), "confirm_password");

        // Current password hash match validation
        if (!BCryptHash.check(dto.getCurrentPassword(), member.getPasswordHashed())) {
            throw new ApiException(401, "Current password is incorrect");
        }

        // Validate same as before
        if (BCryptHash.check(dto.getNewPassword(), member.getPasswordHashed())) {
            throw new ApiException(400, "This was your previous password");
        }

        validateNewPassword(dto.getNewPassword(), dto.getConfirmPassword());

        // Final hash of new password
        String hashedPassword = BCryptHash.hash(dto.getNewPassword());

        // Member final adjustments prior to DB
        member.setPasswordHashed(hashedPassword);

        // DB update on memberId
        memberDAO.update(member);

    }

    // _________________________________________________________________________________________________________________

    public void verifyForgottenPassword(ForgotPasswordVerifyRequestDTO dto) {
        validateNotEmpty(dto.getEmail(), "email");
        validateNotEmpty(dto.getDateOfBirth(), "date_of_birth");

        Member member = memberDAO.findEntityByColumn(dto.getEmail(), Member.Fields.EMAIL);
        if (member == null || !dto.getDateOfBirth().equals(member.getDateOfBirth())) {
            throw new ApiException(401, "E-mail and date of birth do not match");
        }
    }

    // _________________________________________________________________________________________________________________

    public void resetForgottenPassword(MemberRequestDTO dto) {
        ForgotPasswordVerifyRequestDTO verification = new ForgotPasswordVerifyRequestDTO();
        verification.setEmail(dto.getEmail());
        verification.setDateOfBirth(dto.getDateOfBirth());
        verifyForgottenPassword(verification);

        Member member = memberDAO.findEntityByColumn(dto.getEmail(), Member.Fields.EMAIL);
        update(member.getId(), dto);
    }

    // _________________________________________________________________________________________________________________

    private void validateNewPassword(String password, String passwordAgain) {
        validateNotEmpty(password, "password");
        validateNotEmpty(passwordAgain, "password_again");
        if (!password.equals(passwordAgain)) {
            throw new ApiException(400, "New passwords do not match");
        }
        if (password.length() < 8) {
            throw new ApiException(400, "Password must contain at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ApiException(400, "Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new ApiException(400, "Password must contain at least one special character");
        }
    }

}