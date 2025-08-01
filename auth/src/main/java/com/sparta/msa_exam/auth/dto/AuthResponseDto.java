package com.sparta.msa_exam.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private String message;
    private String accessToken;

    public AuthResponseDto(String message) {
        this.message = message;
    }

    public AuthResponseDto(String message, String accessToken) {
        this.message = message;
        this.accessToken = accessToken;
    }
}


