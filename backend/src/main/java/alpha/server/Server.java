package alpha.server;

import alpha.config.DotEnv;
import alpha.config.DotEnvLog;
import alpha.config.HibernateConfig;
import alpha.exception.ApiException;
import alpha.exception.DatabaseException;
import alpha.exception.ResourceNotFoundException;
import alpha.route.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityManagerFactory;
import java.util.Map;

// _____________________________________________________________________________________________________________________

// _____________________________________________________________________________________________________________________

// Important links
// _______________

// 1) - https://javalin.io/documentation#handlers
// 2) - https://javalin.io/news/javalin-7.0.0-stable.html
// 3) - https://www.javadoc.io/doc/io.javalin/javalin/7.0.0-alpha.3/io/javalin/Javalin.html

// _____________________________________________________________________________________________________________________

// _____________________________________________________________________________________________________________________

public class Server {

    // Attributes
    private Javalin app;
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final Integer port = DotEnv.getServerPort();

    // _________________________________________________________________________________________________________________

    public void start() {

        // Singleton check
        if (app != null){
            return;
        }

        // Logging
        DotEnvLog.logEnvInfo();

        // Javalin setup
        app = Javalin.create(config -> {
            configureCors(config);
            configureRouting(config);
            configurePlugins(config);
            configureExceptionHandling(config);
        }).start(port);

    }

    // _________________________________________________________________________________________________________________

    private void configureCors(JavalinConfig javalinConfig) {
        javalinConfig.bundledPlugins.enableCors(cors ->
                cors.addRule(rule -> {
                    rule.anyHost();
                })
        );
        javalinConfig.bundledPlugins.enableHttpAllowedMethodsOnRoutes();
    }

    // _________________________________________________________________________________________________________________

    private void configureRouting(JavalinConfig javalinConfig) {
        javalinConfig.router.contextPath = DotEnv.getApiBasePath();
        javalinConfig.routes.apiBuilder(Routes.registerRoutes(emf));
    }

    // _________________________________________________________________________________________________________________

    private void configurePlugins(JavalinConfig javalinConfig) {
        javalinConfig.bundledPlugins.enableRouteOverview(DotEnv.getRouteOverviewPath());
    }

    // _________________________________________________________________________________________________________________

    private void configureExceptionHandling(JavalinConfig javalinConfig) {

        // ValidationException
        javalinConfig.routes.exception(ValidationException.class, (e, ctx) -> {
            ctx.status(400).json(Map.of(
                    "status", "error",
                    "code", 400,
                    "message", e.getErrors().toString()
            ));
        });

        // ApiException
        javalinConfig.routes.exception(ApiException.class, (e, ctx) -> {
            ctx.status(e.getCode())
                .json(Map.of(
                        "status", "error",
                        "code", e.getCode(),
                        "message", e.getMessage()
                ));
        });

        // ResourceNotFoundException
        javalinConfig.routes.exception(ResourceNotFoundException.class, (e, ctx) -> {
            ctx.status(HttpStatus.NOT_FOUND)
                .json(Map.of(
                        "status", "error",
                        "code", 404,
                        "message", e.getMessage()
                ));
        });

        // DatabaseException
        javalinConfig.routes.exception(DatabaseException.class, (e, ctx) -> {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .json(Map.of(
                        "status", "error",
                        "code", 500,
                        "message", "Database error"
                ));
        });

        // Default Exception Handle
        javalinConfig.routes.exception(Exception.class, (e, ctx) -> {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .json(Map.of(
                        "status", "error",
                        "code", 500,
                        "message", "Internal Server Error"
                ));
        });

    }

    // _________________________________________________________________________________________________________________

    public void stop() {
        if (app != null) {
            app.stop();
            app = null;
        }
    }

    // _________________________________________________________________________________________________________________

    public Javalin getApp() {
        return app;
    }

}