package com.sid.wellness.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.wellness.dto.ReportDto;
import com.sid.wellness.dto.response.ApiResponse;
import com.sid.wellness.entity.Checkin;
import com.sid.wellness.repository.CheckinRepository;


@RestController
@RequestMapping("/api/checkin")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckinController {

    @Autowired
    private CheckinRepository checkinRepository;

    @PostMapping
    public ResponseEntity<?> postCheckin(@RequestBody Checkin checkin, Authentication authentication) {
        
        String userId = (String) authentication.getPrincipal();
    	
    	checkin.setUserId(userId);
    	
		return new ResponseEntity<>(
			    new ApiResponse<>(true, "Check-ins saved successfully", 201, checkinRepository.save(checkin)),
			    HttpStatus.OK
			);
        
    }
    
    @GetMapping("/checkins")
    public ResponseEntity<?> getCheckins(Authentication authentication) {
       	
        String userId = (String) authentication.getPrincipal();
    	
        List<Checkin> userCheckins = checkinRepository.findByUserId(userId);
    	
		return new ResponseEntity<>(
			    new ApiResponse<>(true, "Check-ins fetched successfully",200 , userCheckins),
			    HttpStatus.OK
			);
    	
    }
    
    @GetMapping("/checkins/reports")
    public ResponseEntity<?> getReports(Authentication authentication) {
    	
        String userId = (String) authentication.getPrincipal();
    	
    	List<ReportDto> reportsDtos = checkinRepository.findByUserId(userId).stream()
    		    .map(checkin -> new ReportDto(checkin.getId(), checkin.getProgress(), checkin.getDate()))
    		    .collect(Collectors.toList());
    		
    		return new ResponseEntity<>(
    			    new ApiResponse<>(true, "Reports fetched successfully", 200, reportsDtos),
    			    HttpStatus.OK
    			);
    }
}
