package com.sid.wellness.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.FileInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sid.wellness.dto.request.ReportRequest;
import com.sid.wellness.entity.ReportJob;
import com.sid.wellness.repository.ReportJobRepository;
import com.sid.wellness.service.ReportGenerationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reports/checkins")
public class ReportController {

    @Autowired
    private ReportJobRepository reportJobRepo;

    @Autowired
    private ReportGenerationService reportService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateReport(@RequestBody ReportRequest request) {
        //UUID jobId = UUID.randomUUID();
        ReportJob job = new ReportJob();
        //job.setJobId(jobId);
        job.setUserId(request.getUserId());
        job.setStatus("PENDING");
        ReportJob newJob = reportJobRepo.save(job);

        reportService.generateReport(newJob.getJobId(), request.getUserId());

        return ResponseEntity.ok(Map.of("jobId", newJob.getJobId(), "status", "PENDING"));
    }

    @GetMapping("/status")
    public ResponseEntity<?> checkStatus(@RequestParam UUID jobId) {
        ReportJob job = reportJobRepo.findById(jobId).orElseThrow();
        Map<String, Object> response = new HashMap<>();
        response.put("status", job.getStatus());
        if ("COMPLETED".equals(job.getStatus())) {
            response.put("downloadUrl", "/api/reports/checkins/download/" + jobId);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{jobId}")
    public ResponseEntity<Resource> downloadReport(@PathVariable UUID jobId) throws IOException {
    	ReportJob job = reportJobRepo.findById(jobId)
    	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    	    if (!"COMPLETED".equals(job.getStatus())) {
    	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Report not ready");
    	    }

    	    File file = new File(job.getFilePath());
    	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

    	    return ResponseEntity.ok()
    	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
    	        .contentType(MediaType.APPLICATION_OCTET_STREAM)
    	        .body(resource);
    }
}
