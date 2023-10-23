package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.GoogleOAuth;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Autowired OauthService oauthService;
    @Autowired
    GoogleOAuth googleOAuth;

    @ResponseBody
    @GetMapping("/kakao")
    public ResponseDto<?> kakaoCallback(@RequestParam String code) {
        System.out.println(">>> OauthController.kakaoCalllback");
        System.out.println("code : " + code);

        // 위에서 만든 코드 아래에 코드 추가
        String access_Token = oauthService.getKakaoAccessToken(code);

        return oauthService.saveKaKaoUserInfo(access_Token);
    }

    @GetMapping("/google")
    public ResponseEntity<String> googleCallback(@RequestParam("code") String accessCode) {
        System.out.println(">>> OauthController.googleCallback");
        return oauthService.getGoogleAccessToken(accessCode);
    }

}
