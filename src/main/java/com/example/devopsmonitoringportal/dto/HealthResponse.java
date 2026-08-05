package com.example.devopsmonitoringportal.dto;

import java.util.Map;

/**
 * Health status API payload.
 */
public record HealthResponse(
        String applicationStatus,
        String databaseStatus,
        String diskSpaceStatus,
        String jvmStatus,
        Map<String, String> components
) {
}
