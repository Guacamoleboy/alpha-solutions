package alpha.security.jwt;

import alpha.entity.Member;
import io.jsonwebtoken.Jwts;
import java.util.Date;

public class JwtService extends JwtUtil {

    // Attributes
    protected static final String CLAIM_FIRST_NAME = "first_name";
    protected static final String CLAIM_TYPE = "type";

    // _________________________________________________________________________________________________________________

    public static String generateAccessToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim(CLAIM_FIRST_NAME, member.getFirstName())
                .claim(CLAIM_TYPE, "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    // _________________________________________________________________________________________________________________

    public static String generateRefreshToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim(CLAIM_TYPE, "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    // _________________________________________________________________________________________________________________

    public static Integer getClaimMemberId(String token) {
        return Integer.valueOf(getClaims(token).getSubject());
    }

    // _________________________________________________________________________________________________________________

    public static String getClaimFirstName(String token) {
        return getClaims(token).get(CLAIM_FIRST_NAME, String.class);
    }

    // _________________________________________________________________________________________________________________

    public static String getClaimTokenType(String token) {
        return getClaims(token).get(CLAIM_TYPE, String.class);
    }

}