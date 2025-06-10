package com.sid.wellness.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiError {

	private int status;
    private String error;
    private String message;
    private String path;
    private LocalDate date = LocalDate.now();
    
}
