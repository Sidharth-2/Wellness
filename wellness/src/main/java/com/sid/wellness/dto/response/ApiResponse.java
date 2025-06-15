package com.sid.wellness.dto.response;

import com.sid.wellness.dto.ApiError;

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
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private int status;
    private T data;

}
