package com.example.devopsmonitoringportal.dto;

import java.time.LocalDateTime;

/**
 * Standard API error payload.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
