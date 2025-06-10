package com.sid.wellness.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.wellness.dto.ReportDto;
import com.sid.wellness.entity.Checkin;
import com.sid.wellness.repository.CheckinRepository;


@RestController
@RequestMapping("/api/checkin")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckinController {

    @Autowired
    private CheckinRepository checkinRepository;

    @PostMapping
    public Checkin postCheckin(@RequestBody Checkin checkin) {
        return checkinRepository.save(checkin);
    }
    
    @GetMapping("/checkins")
    public List<Checkin> getCheckins() {
    	
    	return checkinRepository.findAll();
    }
    
    @GetMapping("/checkins/reports")
    public List<ReportDto> getReports() {
    	
    	List<ReportDto> reportsDtos = checkinRepository.findAll().stream()
    		    .map(checkin -> new ReportDto(checkin.getId(), checkin.getProgress(), checkin.getDate()))
    		    .collect(Collectors.toList());

    		return reportsDtos;
    }
}
