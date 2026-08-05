package com.example.devopsmonitoringportal.service;

import com.example.devopsmonitoringportal.entity.ApplicationLog;
import com.example.devopsmonitoringportal.repository.ApplicationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Writes operational logs to persistent storage.
 */
@Service
public class ApplicationLogService {

    private final ApplicationLogRepository applicationLogRepository;

    public ApplicationLogService(ApplicationLogRepository applicationLogRepository) {
        this.applicationLogRepository = applicationLogRepository;
    }

    /**
     * Persists an operational log entry.
     */
    @Transactional
    public void log(String level, String message, String endpoint, Integer statusCode) {
        ApplicationLog entry = new ApplicationLog();
        entry.setLevel(level);
        entry.setMessage(message);
        entry.setEndpoint(endpoint);
        entry.setStatusCode(statusCode);
        applicationLogRepository.save(entry);
    }
}
