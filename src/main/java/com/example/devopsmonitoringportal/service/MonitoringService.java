package com.example.devopsmonitoringportal.service;

import com.example.devopsmonitoringportal.config.BuildInfoProperties;
import com.example.devopsmonitoringportal.dto.BuildResponse;
import com.example.devopsmonitoringportal.dto.DashboardResponse;
import com.example.devopsmonitoringportal.dto.HealthResponse;
import com.example.devopsmonitoringportal.dto.MetricsResponse;
import com.example.devopsmonitoringportal.dto.SystemResponse;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Aggregates dashboard, system and health monitoring data.
 */
@Service
public class MonitoringService {

    private final Environment environment;
    private final BuildInfoProperties buildInfoProperties;
    private final MeterRegistry meterRegistry;
    private final HealthEndpoint healthEndpoint;

    public MonitoringService(
            Environment environment,
            BuildInfoProperties buildInfoProperties,
            MeterRegistry meterRegistry,
            HealthEndpoint healthEndpoint
    ) {
        this.environment = environment;
        this.buildInfoProperties = buildInfoProperties;
        this.meterRegistry = meterRegistry;
        this.healthEndpoint = healthEndpoint;
    }

    /**
     * Builds dashboard summary data.
     */
    public DashboardResponse getDashboardResponse() {
        HealthResponse health = getHealthResponse();
        return new DashboardResponse(
                "DevOps Monitoring Portal",
                buildInfoProperties.getVersion(),
                buildInfoProperties.getBuildNumber(),
                buildInfoProperties.getBuildTimestamp(),
                buildInfoProperties.getEnvironment(),
                getActiveProfiles(),
                System.getProperty("java.version"),
                SpringBootVersion.getVersion(),
                formatUptime(),
                getMemoryUsage(),
                getCpuUsage(),
                getHttpRequestCount(),
                getActiveThreads(),
                health.databaseStatus(),
                health.applicationStatus()
        );
    }

    /**
     * Builds system details data.
     */
    public SystemResponse getSystemResponse() {
        return new SystemResponse(
                System.getProperty("java.version"),
                SpringBootVersion.getVersion(),
                getActiveProfiles(),
                formatUptime(),
                getMemoryUsage(),
                getCpuUsage(),
                getActiveThreads(),
                buildInfoProperties.getEnvironment()
        );
    }

    /**
     * Builds runtime metrics data.
     */
    public MetricsResponse getMetricsResponse() {
        return new MetricsResponse(
                getHttpRequestCount(),
                getActiveThreads(),
                formatUptime(),
                getMemoryUsage(),
                getCpuUsage()
        );
    }

    /**
     * Builds deployment/build metadata response.
     */
    public BuildResponse getBuildResponse() {
        return new BuildResponse(
                buildInfoProperties.getImageTag(),
                buildInfoProperties.getVersion(),
                buildInfoProperties.getDeploymentTime(),
                buildInfoProperties.getGitCommitId(),
                buildInfoProperties.getBranchName(),
                buildInfoProperties.getBuildNumber()
        );
    }

    /**
     * Builds health response from Actuator health contributors.
     */
    public HealthResponse getHealthResponse() {
        HealthComponent root = healthEndpoint.health();
        Map<String, String> componentStatuses = new LinkedHashMap<>();
        String databaseStatus = "UNKNOWN";
        String diskSpaceStatus = "UNKNOWN";
        String jvmStatus = "UNKNOWN";

        if (root instanceof CompositeHealth compositeHealth) {
            for (Map.Entry<String, HealthComponent> entry : compositeHealth.getComponents().entrySet()) {
                String status = extractStatus(entry.getValue());
                componentStatuses.put(entry.getKey(), status);
                if ("db".equalsIgnoreCase(entry.getKey())) {
                    databaseStatus = status;
                }
                if ("diskSpace".equalsIgnoreCase(entry.getKey())) {
                    diskSpaceStatus = status;
                }
                if ("ping".equalsIgnoreCase(entry.getKey()) || "livenessState".equalsIgnoreCase(entry.getKey())) {
                    jvmStatus = status;
                }
            }
        } else if (root instanceof Health health) {
            componentStatuses.put("application", health.getStatus().getCode());
        }

        return new HealthResponse(
                extractStatus(root),
                databaseStatus,
                diskSpaceStatus,
                jvmStatus,
                componentStatuses
        );
    }

    private String getActiveProfiles() {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
            return "default";
        }
        return String.join(", ", Arrays.asList(profiles));
    }

    private String formatUptime() {
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        Duration duration = Duration.ofMillis(uptimeMs);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return "%dh %dm %ds".formatted(hours, minutes, seconds);
    }

    private String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedBytes = runtime.totalMemory() - runtime.freeMemory();
        long maxBytes = runtime.maxMemory();
        long usedMb = usedBytes / (1024 * 1024);
        long maxMb = maxBytes / (1024 * 1024);
        return usedMb + " MB / " + maxMb + " MB";
    }

    private String getCpuUsage() {
        java.lang.management.OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean sunBean) {
            double load = sunBean.getCpuLoad();
            if (load >= 0) {
                return "%.2f%%".formatted(load * 100);
            }
        }
        return "N/A";
    }

    private long getHttpRequestCount() {
        Timer timer = meterRegistry.find("http.server.requests").timer();
        return timer == null ? 0L : (long) timer.count();
    }

    private int getActiveThreads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        return threadMXBean.getThreadCount();
    }

    private String extractStatus(HealthComponent component) {
        if (component instanceof Health health) {
            return health.getStatus().getCode();
        }
        if (component instanceof CompositeHealth compositeHealth) {
            return compositeHealth.getStatus().getCode();
        }
        return "UNKNOWN";
    }
}
