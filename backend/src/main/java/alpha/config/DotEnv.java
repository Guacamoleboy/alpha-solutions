package alpha.config;

import alpha.exception.ApiException;
import io.github.cdimascio.dotenv.Dotenv;

// Created by: Guacamoleboy
// ________________________
// Last updated: 23/03-2026
// By: Guacamoleboy

public class DotEnv {

    // Attributes
    private static final Dotenv dotenv;
    private static final String environment = "development";
    private static final String fileName;
    private static final String directoyPath;

    // _________________________________________________________________________________________________________________
    // Usage:
    // ______
    // DotEnv.getTmdbKey(). Returns a String.

    static {

        // Environment setup + file definition
        // set.env is used for terminal access (change of .env file).
        // For example running the program in terminal with test as environement will
        // load .env.test. If there's no test available it'll fall back to our environement attribute.

        String environmentLoad = System.getProperty("set.env", environment);
        fileName = ".env." + environmentLoad;

        // Directory Setup
        directoyPath = "production".equalsIgnoreCase(environmentLoad) ? "./" : "src/main/resources";

        // Load (I/O) the .env.development file
        dotenv = Dotenv.configure()
                .directory(directoyPath)
                .filename(fileName)
                .ignoreIfMissing()
                .systemProperties()
                .load();
    }

    // _________________________________________________________________________________________________________________

    public static String get(String key) {

        // Deployment
        String value = System.getenv(key);

        // Development
        if (value == null || value.isEmpty()) {
            value = dotenv.get(key);
        }

        // Validation
        if (value == null || value.isEmpty()) {
            throw new ApiException(500, key + " missing");
        }

        return value;
    }

    // _________________________________________________________________________________________________________________

    public static int getServerPort() {
        String port = get("SERVER_PORT");
        if (port == null || port.isEmpty()) {
            throw new ApiException(500, "SERVER_PORT missing in " + fileName);
        }
        try {
            return Integer.parseInt(port);
        } catch (NumberFormatException nfe) {
            throw new ApiException(500, "SERVER_PORT in " + fileName + " is not a valid Integer");
        }
    }

    // _________________________________________________________________________________________________________________

    public static String getApiBasePath() {
        String returnedPath = get("API_BASE_PATH");
        if (returnedPath == null || returnedPath.isEmpty()) {
            throw new ApiException(500, "API_BASE_PATH missing in " + fileName);
        }
        return returnedPath;
    }

    // _________________________________________________________________________________________________________________

    public static String getRouteOverviewPath() {
        String returnedPath = get("ROUTE_ENDPOINT_FOLDER");
        if (returnedPath == null || returnedPath.isEmpty()) {
            throw new ApiException(500, "ROUTE_ENDPOINT_FOLDER missing in " + fileName);
        }
        return returnedPath;
    }

    // _________________________________________________________________________________________________________________

    public static String getUrlPath() {
        String returnedPath = get("URL_PATHING");
        if (returnedPath == null || returnedPath.isEmpty()) {
            throw new ApiException(500, "URL_PATHING missing in " + fileName);
        }
        return returnedPath;
    }

    // _________________________________________________________________________________________________________________

    public static String getFileName() {
        return fileName;
    }

}