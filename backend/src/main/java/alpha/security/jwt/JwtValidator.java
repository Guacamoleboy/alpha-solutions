package alpha.security.jwt;

import alpha.domain.member.dao.MemberDAO;
import alpha.domain.member.entity.Member;
import alpha.exception.ApiException;
import alpha.util.ContextHelper;
import io.javalin.http.Context;

public class JwtValidator {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static boolean isValid(Context ctx) {
        String token = ContextHelper.extractBearerToken(ctx);
        return token != null && JwtUtil.isValid(token);
    }

    // _________________________________________________________________________________________________________________

    public static Member getMemberFromToken(Context ctx, MemberDAO memberDAO) {
        String token = ContextHelper.extractBearerToken(ctx);
        if (!JwtUtil.isValid(token)) {
            throw new ApiException(401, "Invalid token");
        }
        Integer memberId = JwtService.getClaimMemberId(token);
        Member member = memberDAO.getById(memberId);
        ContextHelper.notNull(member, "Member");
        return member;
    }

}
