package alpha.service.auth;

import alpha.dao.impl.MemberDAO;
import alpha.dto.request.LoginRequestDTO;
import alpha.dto.request.MemberRequestDTO;
import alpha.dto.request.RefreshTokenRequestDTO;
import alpha.dto.response.AuthResponseDTO;
import alpha.dto.response.MemberResponseDTO;
import alpha.entity.Member;
import alpha.exception.ApiException;
import alpha.mapper.request.MemberRequestMapper;
import alpha.mapper.response.MemberResponseMapper;
import alpha.security.jwt.JwtService;
import alpha.security.jwt.JwtUtil;
import alpha.util.BCryptHash;
import jakarta.persistence.EntityManager;
import java.sql.Timestamp;

public class AuthService {

    // Attributes
    private final MemberDAO memberDAO;

    // _________________________________________________________________________________________________________________

    public AuthService(EntityManager em) {
        this.memberDAO = new MemberDAO(em);
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