package com.example.devopsmonitoringportal.dto;

/**
 * Runtime metrics API payload.
 */
public record MetricsResponse(
        long totalHttpRequests,
        int activeThreads,
        String jvmUptime,
        String memoryUsage,
        String cpuUsage
) {
}
