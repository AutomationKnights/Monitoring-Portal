package com.example.devopsmonitoringportal.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Admin credentials for in-memory authentication.
 */
@Validated
@ConfigurationProperties(prefix = "app.security")
public class SecurityAdminProperties {

    @NotBlank
    private String adminUsername;
    @NotBlank
    private String adminPassword;

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
