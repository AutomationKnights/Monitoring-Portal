package com.example.devopsmonitoringportal.service;

import com.example.devopsmonitoringportal.config.BuildInfoProperties;
import com.example.devopsmonitoringportal.entity.DeploymentHistory;
import com.example.devopsmonitoringportal.repository.DeploymentHistoryRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles deployment history persistence and retrieval.
 */
@Service
public class DeploymentHistoryService {

    private final DeploymentHistoryRepository deploymentHistoryRepository;
    private final BuildInfoProperties buildInfoProperties;

    public DeploymentHistoryService(
            DeploymentHistoryRepository deploymentHistoryRepository,
            BuildInfoProperties buildInfoProperties
    ) {
        this.deploymentHistoryRepository = deploymentHistoryRepository;
        this.buildInfoProperties = buildInfoProperties;
    }

    /**
     * Returns all deployment records ordered by recency.
     */
    @Transactional(readOnly = true)
    public List<DeploymentHistory> findAll() {
        return deploymentHistoryRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getDeploymentTime().compareTo(a.getDeploymentTime()))
                .toList();
    }

    /**
     * Inserts the current deployment snapshot if it is new.
     */
    @Transactional
    public void storeCurrentDeploymentIfNeeded() {
        Optional<DeploymentHistory> latestOpt = deploymentHistoryRepository.findTopByOrderByDeploymentTimeDesc();
        if (latestOpt.isPresent()) {
            DeploymentHistory latest = latestOpt.get();
            boolean sameDeployment = latest.getVersion().equals(buildInfoProperties.getVersion())
                    && latest.getBuildNumber().equals(buildInfoProperties.getBuildNumber())
                    && latest.getImageTag().equals(buildInfoProperties.getImageTag());
            if (sameDeployment) {
                return;
            }
        }

        DeploymentHistory deploymentHistory = new DeploymentHistory();
        deploymentHistory.setVersion(buildInfoProperties.getVersion());
        deploymentHistory.setBuildNumber(buildInfoProperties.getBuildNumber());
        deploymentHistory.setEnvironment(buildInfoProperties.getEnvironment());
        deploymentHistory.setImageTag(buildInfoProperties.getImageTag());
        deploymentHistory.setStatus("DEPLOYED");
        deploymentHistory.setGitCommitId(buildInfoProperties.getGitCommitId());
        deploymentHistory.setBranchName(buildInfoProperties.getBranchName());
        deploymentHistory.setDeploymentTime(parseDateTime(buildInfoProperties.getDeploymentTime()));
        deploymentHistoryRepository.save(deploymentHistory);
    }

    private LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException ex) {
            return LocalDateTime.now();
        }
    }
}
