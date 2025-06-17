package com.sid.wellness.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "report_jobs")
public class ReportJob {

	@Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "job_id", columnDefinition = "BINARY(16)")
    private UUID jobId;

    @Column(name = "user_id")
    private String userId;
    private String status;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Version
    private Long version;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters & Setters
}

