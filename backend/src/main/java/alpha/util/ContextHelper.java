package alpha.util;

import alpha.exception.ApiException;
import io.javalin.http.Context;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.UUID;

// Created by: Guacamoleboy
// ________________________
// Last updated: 23/03-2026
// By: Guacamoleboy

public class ContextHelper {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static String pathString(Context ctx, String name) {
        return ctx.pathParamAsClass(name, String.class)
                .check(s -> !s.isBlank(), name + " can't be empty")
                .getOrThrow(errors -> new ApiException(400, errors.values().toString()));
    }

    // _________________________________________________________________________________________________________________

    public static Integer pathInt(Context ctx, String name) {
        return ctx.pathParamAsClass(name, Integer.class)
                .check(i -> i > 0, name + " must be > 0")
                .getOrThrow(errors -> new ApiException(400, errors.values().toString()));
    }

    // _________________________________________________________________________________________________________________

    public static Long pathLong(Context ctx, String name) {
        return ctx.pathParamAsClass(name, Long.class)
                .check(l -> l > 0, name + " must be > 0")
                .getOrThrow(errors -> new ApiException(400, errors.values().toString()));
    }

    // _________________________________________________________________________________________________________________

    public static LocalDate pathDate(Context ctx, String name) {
        return ctx.pathParamAsClass(name, LocalDate.class)
                .getOrThrow(errors -> new ApiException(400, errors.values().toString()));
    }

    // _________________________________________________________________________________________________________________

    public static UUID pathUUID(Context ctx, String name) {
        return ctx.pathParamAsClass(name, java.util.UUID.class)
                .getOrThrow(errors -> new ApiException(400, "Invalid UUID format for " + name));
    }

    // _________________________________________________________________________________________________________________

    public static LocalTime pathTime(Context ctx, String name) {
        return ctx.pathParamAsClass(name, LocalTime.class)
                .getOrThrow(errors -> new ApiException(400, errors.values().toString()));
    }

    // _________________________________________________________________________________________________________________

    public static Boolean pathBoolean(Context ctx, String name) {
        return ctx.pathParamAsClass(name, Boolean.class)
                .getOrThrow(errors -> new ApiException(400, errors.values().toString()));
    }

    // _________________________________________________________________________________________________________________

    public static <T> T notNull(T object, String name) {
        if (object == null) {
            throw new ApiException(404, name + " not found");
        }
        return object;
    }

    // _________________________________________________________________________________________________________________

    public static String extractBearerToken(Context ctx) {
        String header = ctx.header("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ApiException(401, "Missing or invalid Authorization header");
        }
        return header.substring(7);
    }

    // _________________________________________________________________________________________________________________

    public static String extractApiKey(Context ctx) {
        String apiKey = ctx.header("X-API-Key");
        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiException(401, "Missing or invalid X-API-Key header");
        }
        return apiKey;
    }

}