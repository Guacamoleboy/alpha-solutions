package alpha.domain.membership.controller;

import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.dto.request.MembershipRequestDTO;
import alpha.domain.membership.mapper.response.MembershipResponseMapper;
import alpha.service.EntityManagerService;
import alpha.crud.CRUDController;
import alpha.domain.membership.service.MembershipService;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class MembershipController extends CRUDController<Membership> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MembershipController(EntityManagerService<Membership> service) {
        super(service, Membership.class, MembershipResponseMapper::toDTO);
    }

    @Override
    public void update(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            Integer id = Integer.valueOf(ctx.pathParam("id"));
            MembershipRequestDTO dto = ctx.bodyAsClass(MembershipRequestDTO.class);
            Membership updated = ((MembershipService) classService).updateMembership(id, dto);
            return MembershipResponseMapper.toDTO(updated);
        }, "Membership updated");
    }

}

