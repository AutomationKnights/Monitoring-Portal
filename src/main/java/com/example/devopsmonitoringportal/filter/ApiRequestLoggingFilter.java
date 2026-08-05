package com.example.devopsmonitoringportal.filter;

import com.example.devopsmonitoringportal.service.ApplicationLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Logs API requests and stores them in database.
 */
@Component
public class ApiRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestLoggingFilter.class);
    private final ApplicationLogService applicationLogService;

    public ApiRequestLoggingFilter(ApplicationLogService applicationLogService) {
        this.applicationLogService = applicationLogService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long duration = System.currentTimeMillis() - start;

        String msg = "%s %s completed in %d ms".formatted(request.getMethod(), request.getRequestURI(), duration);
        LOGGER.info(msg);
        applicationLogService.log("INFO", msg, request.getRequestURI(), response.getStatus());
    }
}
