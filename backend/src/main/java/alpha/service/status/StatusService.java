package alpha.service.status;

import alpha.config.DotEnv;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class StatusService {

    // Attributes
    private final String version;
    private final String environment;
    private final Instant startTime;

    // _________________________________________________________________________________________________________________

    public StatusService() {
        this.version = DotEnv.get("APP_VERSION");
        this.environment = DotEnv.get("APP_ENV");
        this.startTime = Instant.now();
    }

    // _________________________________________________________________________________________________________________

    public Map<String, Object> getStatus() {
        return Map.of(
                "status", "OK",
                "version", version,
                "environment", environment,
                "uptime_seconds", Duration.between(startTime, Instant.now()).getSeconds(),
                "timestamp", Instant.now().toString()
        );
    }

}