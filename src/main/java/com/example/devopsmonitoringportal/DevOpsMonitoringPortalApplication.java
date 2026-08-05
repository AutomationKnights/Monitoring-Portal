package com.example.devopsmonitoringportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Entry point for DevOps Monitoring Portal.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class DevOpsMonitoringPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevOpsMonitoringPortalApplication.class, args);
    }
}
