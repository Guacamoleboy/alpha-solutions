package alpha.domain.auth.service;

import alpha.domain.auth.dto.request.LoginRequestDTO;
import alpha.domain.auth.dto.request.RefreshTokenRequestDTO;
import alpha.domain.auth.dto.response.AuthResponseDTO;
import alpha.security.jwt.JwtService;
import alpha.security.jwt.JwtUtil;
import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.dto.request.MemberRequestDTO;
import alpha.domain.member.dto.response.MemberResponseDTO;
import alpha.domain.member.entity.Member;
import alpha.domain.member.mapper.request.MemberRequestMapper;
import alpha.domain.member.mapper.response.MemberResponseMapper;
import alpha.domain.membership.dao.MembershipDAO;
import alpha.domain.membership.entity.Membership;
import alpha.exception.ApiException;
import alpha.util.BCryptHash;
import jakarta.persistence.EntityManager;
import java.sql.Timestamp;

public class AuthService {

    // Attributes
    private final MemberDAO memberDAO;
    private final MembershipDAO membershipDAO;

    // _________________________________________________________________________________________________________________

    public AuthService(EntityManager em) {
        this.memberDAO = new MemberDAO(em);
        this.membershipDAO = new MembershipDAO(em);
    }

    // _________________________________________________________________________________________________________________

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Member member = memberDAO.findEntityByColumn(
                loginRequestDTO.getEmail(),
                Member.Fields.EMAIL
        );
        if (member == null || !BCryptHash.check(loginRequestDTO.getPassword(), member.getPasswordHashed())) {
            throw new ApiException(401, "Invalid credentials");
        }
        member.setLastLogin(new Timestamp(System.currentTimeMillis()));
        memberDAO.update(member);
        AuthResponseDTO response = new AuthResponseDTO();
        response.setAccessToken(JwtService.generateAccessToken(member));
        response.setRefreshToken(JwtService.generateRefreshToken(member));
        response.setMember(MemberResponseMapper.toDTO(member));
        return response;
    }

    // _________________________________________________________________________________________________________________

    public MemberResponseDTO register(MemberRequestDTO memberRequestDTO) {
        if (memberDAO.existByColumn(memberRequestDTO.getEmail(), Member.Fields.EMAIL)) {
            throw new ApiException(409, "Email already exists");
        }
        Member member = MemberRequestMapper.toEntity(memberRequestDTO);
        member.setPasswordHashed(BCryptHash.hash(memberRequestDTO.getPassword()));
        Membership free = membershipDAO.findEntityByColumn(
                "Free",
                Membership.Fields.NAME
        );
        if (free == null) {
            throw new ApiException(500, "Free membership not found");
        }
        member.setMembership(free);
        memberDAO.create(member);
        return MemberResponseMapper.toDTO(member);
    }

    // _________________________________________________________________________________________________________________

    public AuthResponseDTO refresh(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String refreshToken = refreshTokenRequestDTO.getRefreshToken();
        if (!JwtUtil.isRefreshTokenValid(refreshToken)) {
            throw new ApiException(401, "Invalid refresh token");
        }
        Integer memberId = JwtService.getClaimMemberId(refreshToken);
        Member member = memberDAO.getById(memberId);
        if (member == null) {
            throw new ApiException(404, "Member not found");
        }
        AuthResponseDTO response = new AuthResponseDTO();
        response.setAccessToken(JwtService.generateAccessToken(member));
        return response;
    }

    // _________________________________________________________________________________________________________________

    public MemberResponseDTO me(Integer memberId) {
        Member member = memberDAO.getById(memberId);
        if (member == null) {
            throw new ApiException(404, "Member not found");
        }
        return MemberResponseMapper.toDTO(member);
    }

}
