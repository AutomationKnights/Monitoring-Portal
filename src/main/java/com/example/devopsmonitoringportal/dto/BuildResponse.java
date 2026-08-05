package com.example.devopsmonitoringportal.dto;

/**
 * Build and deployment metadata payload.
 */
public record BuildResponse(
        String imageTag,
        String applicationVersion,
        String deploymentTime,
        String gitCommitId,
        String branchName,
        String buildNumber
) {
}
