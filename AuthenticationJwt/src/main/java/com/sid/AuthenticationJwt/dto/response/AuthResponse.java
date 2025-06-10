package com.sid.AuthenticationJwt.dto.response;

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
public class AuthResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String token;

}
