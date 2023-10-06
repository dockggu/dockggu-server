package com.DXsprint.dockggu.service;


import com.DXsprint.dockggu.dto.KakaoUserInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OauthService {
    public String getKakaoAccessToken(String code) {

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        System.out.println("reqURL : " + reqURL);
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
            sb.append("&redirect_uri=http://localhost:8080/api/oauth/kakao");
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

    public KakaoUserInfo getKakaoUserInfo(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo();

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            String result = conn.toString();

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            JsonElement kakaoAccount = element.getAsJsonObject().get("kakao_account");
            JsonElement profile = kakaoAccount.getAsJsonObject().get("profile");

            //dto에 저장하기
//            kakaoUserInfo.setId(element.getAsJsonObject().get("id").getAsLong());
            kakaoUserInfo.setNickname(profile.getAsJsonObject().get("nickname").getAsString());
            kakaoUserInfo.setProfileImgUrl(profile.getAsJsonObject().get("profile_image_url").getAsString());
//            kakaoUserInfo.setThumnailImgUrl(profile.getAsJsonObject().get("thumbnail_image_url").getAsString());
//            kakaoUserInfo.setHasBirthDay(kakaoAccount.getAsJsonObject().get("has_birthday").getAsBoolean());
//            kakaoUserInfo.setHasGender(kakaoAccount.getAsJsonObject().get("has_gender").getAsBoolean());
//
//            if (kakaoUserInfo.isHasBirthDay()) {
//                kakaoUserInfo.setBirthday(kakaoAccount.getAsJsonObject().get("birthday").getAsString());
//            }
//
//            if (kakaoUserInfo.isHasGender()) {
//                kakaoUserInfo.setGender(kakaoAccount.getAsJsonObject().get("gender").getAsString());
//            }

            System.out.println(kakaoUserInfo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kakaoUserInfo;
    }
}
