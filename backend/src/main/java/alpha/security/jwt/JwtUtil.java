package alpha.security.jwt;

import alpha.config.DotEnv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public abstract class JwtUtil {

    // Attributes
    protected static final String SECRET = DotEnv.get("JWT_SECRET");
    protected static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    protected static final long ACCESS_EXPIRATION = Long.parseLong(DotEnv.get("ACCESS_EXPIRATION"));
    protected static final long REFRESH_EXPIRATION = Long.parseLong(DotEnv.get("REFRESH_EXPIRATION"));

    // _________________________________________________________________________________________________________________

    public static boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // _________________________________________________________________________________________________________________

    public static boolean isAccessTokenValid(String token) {
        return isValid(token) && "access".equals(JwtService.getClaimTokenType(token));
    }

    // _________________________________________________________________________________________________________________

    public static boolean isRefreshTokenValid(String token) {
        return isValid(token) && "refresh".equals(JwtService.getClaimTokenType(token));
    }

    // _________________________________________________________________________________________________________________

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}