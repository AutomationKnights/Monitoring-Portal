package com.example.devopsmonitoringportal.security;

import com.example.devopsmonitoringportal.service.ApplicationLogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Handles successful authentication and logs login activity.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    private final ApplicationLogService applicationLogService;

    public CustomAuthenticationSuccessHandler(ApplicationLogService applicationLogService) {
        this.applicationLogService = applicationLogService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String username = authentication.getName();
        LOGGER.info("Successful login for user: {}", username);
        applicationLogService.log("INFO", "User login: " + username, "/login", HttpServletResponse.SC_OK);
        response.sendRedirect("/dashboard");
    }
}
