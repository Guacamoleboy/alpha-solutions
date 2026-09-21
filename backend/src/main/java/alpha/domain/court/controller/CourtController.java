package alpha.domain.court.controller;

import alpha.domain.court.entity.Court;
import alpha.domain.court.dto.request.CourtRequestDTO;
import alpha.domain.court.mapper.response.CourtResponseMapper;
import alpha.domain.court.service.CourtService;
import alpha.crud.CRUDController;
import alpha.service.EntityManagerService;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class CourtController extends CRUDController<Court> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public CourtController(EntityManagerService<Court> service) {
        super(service, Court.class, CourtResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            CourtRequestDTO dto = ctx.bodyAsClass(CourtRequestDTO.class);
            Court created = ((CourtService) classService).createCourt(dto);
            return CourtResponseMapper.toDTO(created);
        }, "Court created");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void update(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            Integer id = Integer.valueOf(ctx.pathParam("id"));
            CourtRequestDTO dto = ctx.bodyAsClass(CourtRequestDTO.class);
            Court updated = ((CourtService) classService).updateCourt(id, dto);
            return CourtResponseMapper.toDTO(updated);
        }, "Court updated");
    }

}