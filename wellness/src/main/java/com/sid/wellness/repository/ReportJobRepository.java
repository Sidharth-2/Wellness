package com.sid.wellness.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.wellness.entity.ReportJob;

public interface ReportJobRepository extends JpaRepository<ReportJob, UUID> {
    Optional<ReportJob> findById(UUID jobId);
}
