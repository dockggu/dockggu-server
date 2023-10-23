package com.DXsprint.dockggu.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOAuth {
    private final String googleLoginUrl = "https://accounts.google.com";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    @Value("${oauth2.google.client-id}")
    private String googleClientId;
    @Value("${oauth2.google.redirect-uri}")
    private String googleRedirectUrl;
    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    // 일회용 코드를 다시 구글로 보내 엑세스 토큰을 포함한 정보를 받아옴
    public ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if(responseEntity.getStatusCode()== HttpStatus.OK) {
            return responseEntity;
        }

        return null;
    }
}
