package com.DXsprint.dockggu.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/*
JWT : 전자 서명이 된 토큰
JSON 형태로 구성된 토큰
토큰은 {header}.{payload}.{signature} 로 구성

header : typ (해당 토큰 타입), alg (토큰 서명을 위해 사용된 해시 알고리즘)
payload : sub (해당 토큰 주인), iat (토큰 발행 시간), exp (토큰 만료 시간)

*/

@Service
public class TokenProvider {
    // JWT 생성 및 검증을 위한 키
    private static final String SECURITY_KEY = "jwtseckey!@";

    // JWT 생성 메서드
    public String create (String userId) {
        System.out.println("test jwt1");
        // 현재 시간으로부터 1시간을 만료시간으로
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        System.out.println("test jwt2");

        // Jwt 생성 메서드
        return Jwts.builder()
                // 암호화 사용될 알고리즘, 키
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                // JWT 제목, 생성일, 만료일
                .setSubject(userId).setIssuedAt(new Date()).setExpiration(exprTime)
                // 생성
                .compact();
    }

    // JWT검증 (복호화 함수)
    public String validate  (String token) {
        // 매개변수로 받은 token 키를 사용해서 복호화(디코딩)
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        // 복호화된 토큰의 payload에서 제목 가져옴
        return claims.getSubject();
    }
}
