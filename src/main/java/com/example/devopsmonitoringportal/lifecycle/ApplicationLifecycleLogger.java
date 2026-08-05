package com.example.devopsmonitoringportal.lifecycle;

import com.example.devopsmonitoringportal.service.ApplicationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Logs startup and shutdown lifecycle events.
 */
@Component
public class ApplicationLifecycleLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLifecycleLogger.class);
    private final ApplicationLogService applicationLogService;

    public ApplicationLifecycleLogger(ApplicationLogService applicationLogService) {
        this.applicationLogService = applicationLogService;
    }

    @EventListener
    public void onReady(ApplicationReadyEvent event) {
        LOGGER.info("DevOps Monitoring Portal started successfully.");
        applicationLogService.log("INFO", "Application startup complete", "SYSTEM", 200);
    }

    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        LOGGER.info("DevOps Monitoring Portal shutting down.");
        applicationLogService.log("INFO", "Application shutdown initiated", "SYSTEM", 200);
    }
}
