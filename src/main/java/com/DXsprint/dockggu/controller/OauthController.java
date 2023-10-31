package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Autowired OauthService oauthService;

    @ResponseBody
    @GetMapping("/kakao")
    public ResponseDto<?> kakaoCallback(@RequestParam String code) {
        System.out.println(">>> OauthController.kakaoCalllback");
        System.out.println("code : " + code);

        // 위에서 만든 코드 아래에 코드 추가
        String access_Token = oauthService.getKakaoAccessToken(code);

        return oauthService.saveKaKaoUserInfo(access_Token);
    }

//    @GetMapping("/{registrationId}")
//    @GetMapping("/google")
//    public void googleCallback(@RequestParam String code, @PathVariable String registrationId) {
//        System.out.println(">>> OauthController.googleCallback");
//        System.out.println("code = " + code);
//        System.out.println("registrationId = " + registrationId);
//        oauthService.socialLogin(code, registrationId);
//    }

    @GetMapping("/google")
    public void googleCallback(@RequestParam String code) {
        System.out.println(">>> OauthController.googleCallback");
        System.out.println("code = " + code);
//        System.out.println("registrationId = " + registrationId);
        oauthService.socialLogin(code, "google");
    }
}
