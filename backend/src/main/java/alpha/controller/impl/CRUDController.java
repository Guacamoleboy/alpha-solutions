package alpha.controller.impl;

import alpha.service.internal.EntityManagerService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class CRUDController <T> {

    // _________________________________________________________________________________________________________________
    // Attributes

    protected final EntityManagerService<T> classService;
    protected final Class<T> classSpecific;
    private final Function<T, Object> classMapper;

    // _________________________________________________________________________________________________________________

    protected CRUDController(EntityManagerService<T> classService, Class<T> classSpecific, Function<T, Object> classMapper) {
        this.classService = classService;
        this.classSpecific = classSpecific;
        this.classMapper = classMapper;
    }

    // _________________________________________________________________________________________________________________

    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            T entity = ctx.bodyAsClass(classSpecific);
            T created = classService.create(entity);
            return classMapper.apply(created);
        }, classSpecific.getSimpleName() + " created");
    }

    // _________________________________________________________________________________________________________________

    public void update(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            T entity = ctx.bodyValidator(classSpecific)
                    .check(e -> e != null, classSpecific.getSimpleName() + " can't be null")
                    .get();
            T updated = classService.update(entity);
            return classMapper.apply(updated);
        }, classSpecific.getSimpleName() + " updated");
    }

    // _________________________________________________________________________________________________________________

    public void updateById(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String idStr = ctx.pathParam("id");
            T entity = ContextHelper.notNull(classService.getById(idStr), classSpecific.getSimpleName());
            T updated = classService.update(entity);
            return classMapper.apply(updated);
        }, classSpecific.getSimpleName() + " updated");
    }

    // _________________________________________________________________________________________________________________

    public void getById(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String idStr = ctx.pathParam("id");
            UUID uuid = UUID.fromString(idStr);
            T entity = ContextHelper.notNull(classService.getById(uuid), classSpecific.getSimpleName());
            return classMapper.apply(entity);
        }, classSpecific.getSimpleName() + " retrieved");
    }

    // _________________________________________________________________________________________________________________

    public void getAll(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () ->
            classService.getAll().stream().map(classMapper).collect(Collectors.toList())
        , classSpecific.getSimpleName() + " list retrieved");
    }

    // _________________________________________________________________________________________________________________

    public void deleteById(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String idStr = ctx.pathParam("id");
            UUID uuid = UUID.fromString(idStr);
            T entity = ContextHelper.notNull(classService.deleteById(uuid), classSpecific.getSimpleName());
            return classMapper.apply(entity);
        }, classSpecific.getSimpleName() + " deleted");
    }

    // _________________________________________________________________________________________________________________

    public void deleteAll(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () ->
            classService.deleteAll()
        , "All " + classSpecific.getSimpleName() + " deleted");
    }

    // _________________________________________________________________________________________________________________

    public void deleteAllSafe(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () ->
            classService.deleteAllSafe()
        , "All " + classSpecific.getSimpleName() + " safely deleted");
    }

}