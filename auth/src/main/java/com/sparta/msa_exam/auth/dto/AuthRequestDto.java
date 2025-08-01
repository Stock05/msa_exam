package com.sparta.msa_exam.auth.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {
    private String username;
    private String password;
    private String role;

}
