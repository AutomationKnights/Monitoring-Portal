package com.example.devopsmonitoringportal.lifecycle;

import com.example.devopsmonitoringportal.service.DeploymentHistoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Persists the current deployment metadata at startup.
 */
@Component
public class DeploymentHistoryInitializer {

    private final DeploymentHistoryService deploymentHistoryService;

    public DeploymentHistoryInitializer(DeploymentHistoryService deploymentHistoryService) {
        this.deploymentHistoryService = deploymentHistoryService;
    }

    @PostConstruct
    public void init() {
        deploymentHistoryService.storeCurrentDeploymentIfNeeded();
    }
}
