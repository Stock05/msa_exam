package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.AuthRequestDto;
import com.sparta.msa_exam.auth.dto.AuthResponseDto;
import com.sparta.msa_exam.auth.user.entity.User;
import com.sparta.msa_exam.auth.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j


public class AuthService {

    private final UserRepository userRepository;

    @Value("${spring.application.name}")
    private String issuer;
    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;
    private final SecretKey secretKey;

    public AuthService(UserRepository userRepository, @Value("${service.jwt.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    //회원가입
    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        User user = new User(authRequestDto);
        try{
            userRepository.save(user);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("Error while signing up user");
        }

        return new AuthResponseDto("회원가입이 완료되었습니다");
    }

    //로그인
    public AuthResponseDto signIn(AuthRequestDto authRequestDto) {
        User user = userRepository.findByUsername(authRequestDto.getUsername()).orElseThrow(() ->  new IllegalArgumentException("Username not found with name " + authRequestDto.getUsername()));
        if(!user.getPassword().equals(authRequestDto.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken = createAccessToken(user.getId(),user.getUsername());
        return new AuthResponseDto("로그인 성공",accessToken);
    }

    public String createAccessToken(Long user_id, String role){
        return Jwts.builder()
                // 사용자 ID를 클레임으로 설정
                .claim("user_id", user_id)
                .claim("role", role)
                // JWT 발행자를 설정
                .issuer(issuer)
                // JWT 발행 시간을 현재 시간으로 설정
                .issuedAt(new Date(System.currentTimeMillis()))
                // JWT 만료 시간을 설정
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                // SecretKey를 사용하여 HMAC-SHA512 알고리즘으로 서명
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                // JWT 문자열로 컴팩트하게 변환
                .compact();
    }




}
