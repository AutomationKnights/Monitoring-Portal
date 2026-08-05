package com.example.devopsmonitoringportal.dto;

/**
 * System details API payload.
 */
public record SystemResponse(
        String javaVersion,
        String springBootVersion,
        String activeProfiles,
        String uptime,
        String memoryUsage,
        String cpuUsage,
        int activeThreads,
        String environment
) {
}
