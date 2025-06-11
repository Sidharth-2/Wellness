package com.sid.wellness.dto;


import java.text.SimpleDateFormat;
import java.time.Instant;

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
	    //private Instant timestamp = Instant.now();
	    private String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
	    
	    
	    public ApiError(int status, String error, String message, String path) {
	        this.status = status;
	        this.error = error;
	        this.message = message;
	        this.path = path;
	    }
    
}
