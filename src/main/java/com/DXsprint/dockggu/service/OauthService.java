package com.DXsprint.dockggu.service;


import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.UserRepository;
import com.DXsprint.dockggu.security.TokenProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class OauthService {

    @Autowired UserRepository userRepository;
    @Autowired TokenProvider tokenProvider;

    
    @Transactional
    public String getKakaoAccessToken(String code) {
        System.out.println(">>> OauthService.getKakaoAccessToken");
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로 설정
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요처에 필요로 요구하는 파라미터를 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter((new OutputStreamWriter(conn.getOutputStream())));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=155bd25b5b420714ad17441b610b274e");
            //sb.append("&redirect_uri=http://localhost:8081/api/oauth/kakao");
            sb.append("&redirect_uri=http://16.16.217.214:8080/api/oauth/kakao");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @Transactional
    public ResponseDto<?> saveKaKaoUserInfo(String access_Token) {

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

            // 기존 kakao email 존재 여부 확인
            UserEntity userEntity = new UserEntity();
            userEntity = userRepository.findByUserKakaoEmail(email);
            System.out.println(">>>>>>>>>>>>>>>>>>>");

            // 기존 kakao email 없으면 회원가입 진행
            if(userEntity == null) {
                SignUpDto signUpDto = new SignUpDto();

                signUpDto.setUserKakaoEmail(email);
                signUpDto.setUserNickname(nickname);
                signUpDto.setUserAward(0);

                UserEntity userEntity2 = new UserEntity(signUpDto);

                userRepository.save(userEntity2);
            } else {
                // 카카오 로그인 후 토큰 발급
                System.out.println("-------------");
                userEntity = userRepository.findByUserKakaoEmail(email);
                String userId = userEntity.getUserId().toString();
                System.out.println("userId : " + userId);

                String token = tokenProvider.create(userId);
                int exprTime = 3600000;
                SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
                return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Error");
        }
        return ResponseDto.setSuccess("Sign Up Success", null);
    }





//    @Autowired private final UserRepository userRepository;

//    public ResponseEntity<String> getGoogleAccessToken(String accessCode) {
//
//        RestTemplate restTemplate=new RestTemplate();
//        Map<String, String> params = new HashMap<>();
//
//        System.out.println("GOOGLE_CLIENT_ID >>> " + GOOGLE_CLIENT_ID);
//        System.out.println("GOOGLE_CLIENT_SECRET >>> " + GOOGLE_CLIENT_SECRET);
//        System.out.println("LOGIN_REDIRECT_URL >>> " + LOGIN_REDIRECT_URL);
//
//
//        params.put("code", accessCode);
//        params.put("client_id", GOOGLE_CLIENT_ID);
//        params.put("client_secret", GOOGLE_CLIENT_SECRET);
//        params.put("redirect_uri", LOGIN_REDIRECT_URL);
//        params.put("grant_type", "authorization_code");
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params,String.class);
//
//        System.out.println("Param >>> " + params.toString());
//
//        if(responseEntity.getStatusCode() == HttpStatus.OK){
//            return responseEntity;
//        }
//        return null;
//    }

    // ============================

    // Google API
    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public OauthService(Environment env) {
        this.env = env;
    }

    public void socialLogin(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        System.out.println("userResourceNode = " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        //String email = userResourceNode.get("email").asText();
        String name = userResourceNode.get("name").asText();
        System.out.println("id = " + id);
        //System.out.println("email = " + email);
        System.out.println("name = " + name);
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

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
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }





}
