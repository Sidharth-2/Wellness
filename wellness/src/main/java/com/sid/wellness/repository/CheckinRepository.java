package com.sid.wellness.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.wellness.entity.Checkin;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {
	
	List<Checkin> findByUserId(String userId);
}
