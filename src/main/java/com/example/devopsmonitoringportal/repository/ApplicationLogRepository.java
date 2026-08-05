package com.example.devopsmonitoringportal.repository;

import com.example.devopsmonitoringportal.entity.ApplicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for application log persistence.
 */
public interface ApplicationLogRepository extends JpaRepository<ApplicationLog, Long> {
}
