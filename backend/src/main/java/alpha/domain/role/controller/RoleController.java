package alpha.domain.role.controller;

import alpha.crud.CRUDController;
import alpha.domain.role.dto.request.RoleRequestDTO;
import alpha.domain.role.entity.Role;
import alpha.domain.role.mapper.request.RoleRequestMapper;
import alpha.domain.role.mapper.response.RoleResponseMapper;
import alpha.domain.role.service.RoleService;
import alpha.service.EntityManagerService;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class RoleController extends CRUDController<Role> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public RoleController(EntityManagerService<Role> service) {
        super(service, Role.class, RoleResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            RoleRequestDTO dto = ctx.bodyAsClass(RoleRequestDTO.class);
            Role role = RoleRequestMapper.toEntity(dto);
            Role created = ((RoleService) classService).create(role);
            return RoleResponseMapper.toDTO(created);
        }, "Role created");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void update(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            Integer id = Integer.valueOf(ctx.pathParam("id"));
            RoleRequestDTO dto = ctx.bodyAsClass(RoleRequestDTO.class);
            Role role = RoleRequestMapper.toEntity(dto);
            role.setId(id);
            Role updated = ((RoleService) classService).update(role);
            return RoleResponseMapper.toDTO(updated);
        }, "Role updated");
    }

}