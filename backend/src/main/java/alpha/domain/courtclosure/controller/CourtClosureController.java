package alpha.domain.courtclosure.controller;

import alpha.crud.CRUDController;
import alpha.domain.courtclosure.dto.request.CourtClosureRequestDTO;
import alpha.domain.courtclosure.entity.CourtClosure;
import alpha.domain.courtclosure.mapper.response.CourtClosureResponseMapper;
import alpha.domain.courtclosure.service.CourtClosureService;
import alpha.exception.ApiException;
import alpha.security.jwt.JwtService;
import alpha.service.EntityManagerService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;
import java.util.Map;

public class CourtClosureController extends CRUDController<CourtClosure> {

    // _________________________________________________________________________________________________________________

    public CourtClosureController(EntityManagerService<CourtClosure> service) {
        super(service, CourtClosure.class, CourtClosureResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            requireOwner(ctx);
            CourtClosureRequestDTO dto = ctx.bodyAsClass(CourtClosureRequestDTO.class);
            CourtClosure created = ((CourtClosureService) classService).createCourtClosure(dto);
            return CourtClosureResponseMapper.toDTO(created);
        }, "Court closure saved");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void getAll(Context ctx) {
        requireOwner(ctx);
        super.getAll(ctx);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void getById(Context ctx) {
        requireOwner(ctx);
        super.getById(ctx);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void update(Context ctx) {
        ctx.status(405).json(Map.of("status", "error", "message", "Court closures cannot be updated"));
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void deleteById(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            requireOwner(ctx);
            Integer id = Integer.valueOf(ctx.pathParam("id"));
            CourtClosure deleted = classService.deleteById(id);
            if (deleted == null) {
                throw new ApiException(404, "Court closure not found");
            }
            return CourtClosureResponseMapper.toDTO(deleted);
        }, "Court closure removed");
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void deleteAll(Context ctx) {
        requireOwner(ctx);
        super.deleteAll(ctx);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void deleteAllSafe(Context ctx) {
        requireOwner(ctx);
        super.deleteAllSafe(ctx);
    }

    // _________________________________________________________________________________________________________________

    private void requireOwner(Context ctx) {
        String token = ContextHelper.extractBearerToken(ctx);
        if (!"OWNER".equals(JwtService.getClaimRole(token))) {
            throw new ApiException(403, "Only owners can manage court closures");
        }
    }

}