package com.sid.wellness.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.wellness.dto.response.ApiResponse;
import com.sid.wellness.entity.HealthTipDetail;
import com.sid.wellness.repository.HealthTipRepository;
import com.sid.wellness.service.HealthTipService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/healthtips")
public class HealthTipController {

    @Autowired
    private HealthTipService healthTipService;
    
    @Autowired
    private HealthTipRepository healthTipRepository;

    @GetMapping
    public ResponseEntity<?> getBasicTips() {
       
    	
    	List<Map<String, Object>> tipsList =  healthTipRepository.findAll().stream().map(tip -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tip.getId());
            map.put("title", tip.getTitle());
            return map;
        }).collect(Collectors.toList());
        
		return new ResponseEntity<>(
			    new ApiResponse<>(true, "Tip list fetched successfully", tipsList),
			    HttpStatus.OK
			);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getTipDetail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
    	Optional<Object> detailMap =  healthTipRepository.findById(id).map(tip -> {
            HealthTipDetail d = tip.getDetail();

            result.put("id", tip.getId());
            result.put("title", tip.getTitle());
            result.put("detailedDescription", d.getDetailedDescription());
            result.put("imageUrl", d.getImageUrl());
            result.put("source", d.getSource());
            return ResponseEntity.ok(result);
        });
    	
		return new ResponseEntity<>(
			    new ApiResponse<>(true, "Health Tip fetched successfully", result),
			    HttpStatus.OK
			);
    }
}
