package alpha.controller.impl;

import alpha.dto.request.MemberRequestDTO;
import alpha.entity.Member;
import alpha.mapper.response.MemberResponseMapper;
import alpha.service.internal.EntityManagerService;
import alpha.service.internal.MemberService;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

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

}