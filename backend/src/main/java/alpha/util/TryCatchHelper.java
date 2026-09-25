package alpha.util;

import alpha.exception.ApiException;
import io.javalin.http.Context;
import java.util.Map;
import java.util.function.Supplier;

// Created by: Guacamoleboy
// ________________________
// Last updated: 23/03-2026
// By: Guacamoleboy

public class TryCatchHelper {

    // Attributes

    // _________________________________________________________________________________________________________________
    // Supplier <T>
    //      - Needed instead of Runnable as we need a result in return.
    //      - Runnable only executes. Doesn't return anything.

    public static <T> void tryCatchHelper(Context ctx, Supplier<T> supplier, String successMessage) {
        try {

            T result = supplier.get();

            if (result == null) {
                ctx.status(200).json(Map.of(
                        "status", "success",
                        "message", successMessage
                ));
            } else {
                ctx.status(200).json(Map.of(
                        "status", "success",
                        "message", successMessage,
                        "data", result
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            int statusCode = e instanceof ApiException apiException
                    ? apiException.getCode()
                    : 500;
            ctx.status(statusCode).json(Map.of(
                    "status", "error",
                    "message", e.getMessage() != null
                            ? e.getMessage()
                            : "Internal Server Error",
                    "code", statusCode
            ));
        }

    }

    // _________________________________________________________________________________________________________________
    // Void specific. Using Runnable instead of Supplier<T>

    public static void tryCatchHelperVoid(Context ctx, Runnable runnable, String successMessage) {

        // Runnable
        runnable.run();

        ctx.status(200).json(Map.of(
                "status", "success",
                "message", successMessage
        ));

    }

}