package com.example.devopsmonitoringportal.repository;

import com.example.devopsmonitoringportal.entity.DeploymentHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for deployment history records.
 */
public interface DeploymentHistoryRepository extends JpaRepository<DeploymentHistory, Long> {

    Optional<DeploymentHistory> findTopByOrderByDeploymentTimeDesc();
}
