package com.example.devopsmonitoringportal.dto;

/**
 * Dashboard API payload.
 */
public record DashboardResponse(
        String applicationName,
        String currentVersion,
        String buildNumber,
        String buildTimestamp,
        String environment,
        String activeSpringProfile,
        String javaVersion,
        String springBootVersion,
        String jvmUptime,
        String memoryUsage,
        String cpuUsage,
        long totalHttpRequests,
        int activeThreads,
        String databaseStatus,
        String applicationHealth
) {
}
