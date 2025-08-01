package com.sparta.msa_exam.auth.controller;


import com.sparta.msa_exam.auth.dto.AuthRequestDto;
import com.sparta.msa_exam.auth.dto.AuthResponseDto;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public AuthResponseDto signUp(@RequestBody AuthRequestDto authRequestDto) {
        return authService.signUp(authRequestDto);
    }

    @PostMapping("/sign-in")
    public AuthResponseDto signIn(@RequestBody AuthRequestDto authRequestDto) {
        return authService.signIn(authRequestDto);
    }






}
