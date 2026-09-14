package alpha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DotEnvLog {

    // Attributes
    private static final Logger LOGGER = LoggerFactory.getLogger(DotEnvLog.class);

    // _________________________________________________________________________________________________________________

    public static void logEnvInfo() {
        try {
            LOGGER.info("\n\n");
            LOGGER.info("############## | DotEnv Logger | ##############");
            LOGGER.info("Environment File    : {}", DotEnv.getFileName());
            LOGGER.info("Server Port         : {}", DotEnv.getServerPort());
            LOGGER.info("API Base Path       : {}", DotEnv.getApiBasePath());
            LOGGER.info("Route Folder        : {}", DotEnv.getRouteOverviewPath());
            LOGGER.info("URL Path            : {}", DotEnv.getUrlPath());
            LOGGER.info("Server Path in Use  : {}:{}", DotEnv.getUrlPath(),DotEnv.getServerPort());
            LOGGER.info("###############################################\n\n");
        } catch (Exception e) {
            LOGGER.error("Error reading .env values: {}", e.getMessage(), e);
            throw e;
        }
    }

}
