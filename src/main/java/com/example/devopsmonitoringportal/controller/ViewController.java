package com.example.devopsmonitoringportal.controller;

import com.example.devopsmonitoringportal.service.DeploymentHistoryService;
import com.example.devopsmonitoringportal.service.MonitoringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Thymeleaf views for monitoring dashboard.
 */
@Controller
public class ViewController {

    private final MonitoringService monitoringService;
    private final DeploymentHistoryService deploymentHistoryService;

    public ViewController(
            MonitoringService monitoringService,
            DeploymentHistoryService deploymentHistoryService
    ) {
        this.monitoringService = monitoringService;
        this.deploymentHistoryService = deploymentHistoryService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("dashboard", monitoringService.getDashboardResponse());
        model.addAttribute("build", monitoringService.getBuildResponse());
        model.addAttribute("health", monitoringService.getHealthResponse());
        model.addAttribute("metrics", monitoringService.getMetricsResponse());
        return "dashboard";
    }

    @GetMapping("/deployment")
    public String deployment(Model model) {
        model.addAttribute("build", monitoringService.getBuildResponse());
        model.addAttribute("deployments", deploymentHistoryService.findAll());
        return "deployment";
    }

    @GetMapping("/health")
    public String health(Model model) {
        model.addAttribute("health", monitoringService.getHealthResponse());
        return "health";
    }
}
