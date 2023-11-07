package com.DXsprint.dockggu.service;


import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.UserRepository;
import com.DXsprint.dockggu.security.TokenProvider;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OauthService {

    @Autowired private UserRepository userRepository;
    @Autowired private TokenProvider tokenProvider;
    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public OauthService(Environment env) {
        this.env = env;
    }

    public ResponseDto<?> socialLogin(String accessToken, String registrationId) {
//        System.out.println(">>> OauthService.socialLogin");
//        String accessToken = getAccessToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);

        System.out.println("userResourceNode : " + userResourceNode.toString());

        SocialUserResponseDto userResource = new SocialUserResponseDto();
        switch (registrationId) {
            case "google": {
                userResource.setEmail(userResourceNode.get("email").asText());
                userResource.setNickname(userResourceNode.get("name").asText());
                break;
            } case "kakao": {
                userResource.setId(userResourceNode.get("id").asText());
                userResource.setEmail(userResourceNode.get("kakao_account").get("email").asText());
                userResource.setNickname(userResourceNode.get("kakao_account").get("profile").get("nickname").asText());
                break;
            } case "naver": {
                userResource.setId(userResourceNode.get("response").get("id").asText());
                userResource.setEmail(userResourceNode.get("response").get("email").asText());
                userResource.setNickname(userResourceNode.get("response").get("nickname").asText());
                break;
            } default: {
                throw new RuntimeException("UNSUPPORTED SOCIAL TYPE");
            }
        }

        System.out.println("userResource = " + userResource);
        System.out.println("email = {}" + userResource.getEmail());
        System.out.println("nickname {}" + userResource.getNickname());
        System.out.println("======================================================");



        // 기존 email 존재 여부 확인
        UserEntity userEntity = new UserEntity();
        userEntity = userRepository.findByUserSocialEmail(userResource.getEmail());
        // 기존 email 없으면 회원가입 진행
        if(userEntity == null) {
            SignUpDto signUpDto = new SignUpDto();

            signUpDto.setUserSocialEmail(userResource.getEmail());
            signUpDto.setUserNickname(userResource.getNickname());
            signUpDto.setUserAward(0);

            userEntity = new UserEntity(signUpDto);

            userRepository.save(userEntity);
            return ResponseDto.setSuccess("Sign Up Success", null);

        } else {
            // 로그인 후 토큰 발급
            userEntity = userRepository.findByUserSocialEmail(userResource.getEmail());
            String userId = userEntity.getUserId().toString();
            System.out.println("userId : " + userId);

            String token = tokenProvider.create(userId);
            int exprTime = 360000000;
            SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
            return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
        }
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");
        String scope = env.getProperty("oauth2." + registrationId + ".scope");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();

        System.out.println("accessTokenNode : " + accessTokenNode.toString());

        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        System.out.println(">>> OauthService.getUserResource");
        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

}
