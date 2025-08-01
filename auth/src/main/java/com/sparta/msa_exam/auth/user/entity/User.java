package com.sparta.msa_exam.auth.user.entity;

import com.sparta.msa_exam.auth.dto.AuthRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    public User(AuthRequestDto authRequestDto) {
        this.username = authRequestDto.getUsername();
        this.password = authRequestDto.getPassword();
        this.role = authRequestDto.getRole();
    }
}
