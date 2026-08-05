package com.example.devopsmonitoringportal.controller;

import com.example.devopsmonitoringportal.dto.BuildResponse;
import com.example.devopsmonitoringportal.dto.DashboardResponse;
import com.example.devopsmonitoringportal.dto.HealthResponse;
import com.example.devopsmonitoringportal.dto.MetricsResponse;
import com.example.devopsmonitoringportal.dto.SystemResponse;
import com.example.devopsmonitoringportal.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for monitoring data.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final MonitoringService monitoringService;

    public ApiController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(monitoringService.getDashboardResponse());
    }

    @GetMapping("/system")
    public ResponseEntity<SystemResponse> getSystem() {
        return ResponseEntity.ok(monitoringService.getSystemResponse());
    }

    @GetMapping("/metrics")
    public ResponseEntity<MetricsResponse> getMetrics() {
        return ResponseEntity.ok(monitoringService.getMetricsResponse());
    }

    @GetMapping("/build")
    public ResponseEntity<BuildResponse> getBuild() {
        return ResponseEntity.ok(monitoringService.getBuildResponse());
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> getHealth() {
        return ResponseEntity.ok(monitoringService.getHealthResponse());
    }
}
