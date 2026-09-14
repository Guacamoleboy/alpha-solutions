package alpha.controller.auth;

import alpha.dto.request.LoginRequestDTO;
import alpha.dto.request.MemberRequestDTO;
import alpha.dto.request.RefreshTokenRequestDTO;
import alpha.security.jwt.JwtService;
import alpha.service.auth.AuthService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;

public class AuthController {

    // Attributes
    private final AuthService authService;

    // _________________________________________________________________________________________________________________

    public AuthController(EntityManager em) {
        this.authService = new AuthService(em);
    }

    // _________________________________________________________________________________________________________________

    public void login(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            LoginRequestDTO loginRequestDTO = ctx.bodyAsClass(LoginRequestDTO.class);
            return authService.login(loginRequestDTO);
        }, "Login successful");
    }

    // _________________________________________________________________________________________________________________

    public void register(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            MemberRequestDTO memberRequestDTO = ctx.bodyAsClass(MemberRequestDTO.class);
            return authService.register(memberRequestDTO);
        }, "Member registered successfully");
    }

    // _________________________________________________________________________________________________________________

    public void refresh(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            RefreshTokenRequestDTO refreshTokenRequestDTO = ctx.bodyAsClass(RefreshTokenRequestDTO.class);
            return authService.refresh(refreshTokenRequestDTO);
        }, "Token refreshed");
    }

    // _________________________________________________________________________________________________________________

    public void me(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            Integer memberId = JwtService.getClaimMemberId(token);
            return authService.me(memberId);
        }, "Member retrieved");
    }

}