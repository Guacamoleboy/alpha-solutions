package alpha.domain.member.controller;

import alpha.domain.member.dto.request.MemberRequestDTO;
import alpha.domain.member.entity.Member;
import alpha.domain.member.mapper.response.MemberResponseMapper;
import alpha.domain.member.service.MemberService;
import alpha.crud.CRUDController;
import alpha.service.EntityManagerService;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;
import alpha.domain.member.dto.request.MemberPasswordRequestDTO;
import alpha.security.jwt.JwtService;
import alpha.util.ContextHelper;

public class MemberController extends CRUDController<Member> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MemberController(EntityManagerService<Member> service) {
        super(service, Member.class, MemberResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            Member member = ctx.bodyAsClass(Member.class);
            Member created = ((MemberService) classService).createMember(member);
            return MemberResponseMapper.toDTO(created);
        }, "Member created");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void update(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            Integer id = Integer.valueOf(ctx.pathParam("id"));
            MemberRequestDTO dto = ctx.bodyAsClass(MemberRequestDTO.class);
            return ((MemberService) classService).update(id, dto);
        }, "Member updated");
    }

    // _________________________________________________________________________________________________________________

    public void updatePassword(Context ctx) {
        TryCatchHelper.tryCatchHelperVoid(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            Integer memberId = JwtService.getClaimMemberId(token);
            MemberPasswordRequestDTO dto = ctx.bodyAsClass(MemberPasswordRequestDTO.class);
            ((MemberService) classService).updatePassword(memberId, dto);
        }, "Password updated");
    }

}