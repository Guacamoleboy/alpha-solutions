package alpha.domain.resources.staff.controller;

import alpha.crud.CRUDController;
import alpha.domain.resources.staff.dto.request.StaffRequestDTO;
import alpha.domain.resources.staff.entity.Staff;
import alpha.domain.resources.staff.mapper.request.StaffRequestMapper;
import alpha.domain.resources.staff.mapper.response.StaffResponseMapper;
import alpha.domain.resources.staff.service.StaffService;
import alpha.service.EntityManagerService;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class StaffController extends CRUDController<Staff> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public StaffController(EntityManagerService<Staff> service) {
        super(service, Staff.class, StaffResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            StaffRequestDTO dto = ctx.bodyAsClass(StaffRequestDTO.class);
            Staff created = ((StaffService) classService).createStaff(StaffRequestMapper.toEntity(dto));
            return StaffResponseMapper.toDTO(created);
        }, "Staff created");
    }

    @Override
    public void update(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            Integer id = Integer.valueOf(ctx.pathParam("id"));
            StaffRequestDTO dto = ctx.bodyAsClass(StaffRequestDTO.class);
            Staff updated = ((StaffService) classService).updateStaff(id, dto);
            return StaffResponseMapper.toDTO(updated);
        }, "Staff updated");
    }

}
