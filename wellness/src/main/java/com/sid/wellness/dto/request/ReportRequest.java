package com.sid.wellness.dto.request;

import java.time.LocalDate;
import java.util.UUID;


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
public class ReportRequest {
    private String userId;
    private LocalDate from;
    private LocalDate to;
    // Getters and Setters
}
