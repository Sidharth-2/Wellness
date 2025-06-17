package com.sid.wellness.service;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sid.wellness.entity.Checkin;
import com.sid.wellness.entity.ReportJob;
import com.sid.wellness.repository.CheckinRepository;
import com.sid.wellness.repository.ReportJobRepository;

import jakarta.transaction.Transactional;

@Service
public class ReportGenerationService {

    @Autowired
    private ReportJobRepository reportJobRepository;
    
    @Autowired
    private CheckinRepository checkInRepository;


    @Async
    @Transactional
    public void generateReport(UUID jobId, String userId) {
        Optional<ReportJob> jobOpt = reportJobRepository.findById(jobId);
        if (jobOpt.isEmpty()) return;

        ReportJob job = jobOpt.get();
        job.setStatus("IN_PROGRESS");
        reportJobRepository.save(job);

        try {
            List<Checkin> checkIns = checkInRepository.findByUserId(userId.toString());

            File file = new File("reports/" + jobId + ".csv");
            file.getParentFile().mkdirs(); // ensure folder exists
            
            System.out.println(file.getAbsolutePath());

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("CheckInDate,Progress, Mood, Notes");
                for (Checkin c : checkIns) {
                    writer.println(c.getDate() + "," + c.getProgress() + "," + c.getMood() + "," + c.getNotes());
                    System.out.println(c.getId());
                }
            }
            
            ReportJob refreshedJob = reportJobRepository.findById(job.getJobId()).get();
            refreshedJob.setStatus("COMPLETED");
            refreshedJob.setFilePath(file.getAbsolutePath());
        } catch (Exception e) {
            job.setStatus("FAILED");
        }

        reportJobRepository.save(job);
    }
}
