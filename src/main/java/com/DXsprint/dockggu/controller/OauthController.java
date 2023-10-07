package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Autowired OauthService oauthService;

    @ResponseBody
    @GetMapping("/kakao")
    public ResponseDto<?> kakaoCalllback(@RequestParam String code) {
        System.out.println(">>> OauthController.kakaoCalllback");
        System.out.println("code : " + code);

        ResponseDto<?> result = null;

        // 위에서 만든 코드 아래에 코드 추가
        String access_Token = oauthService.getKakaoAccessToken(code);
        // 위에서 만든 코드 아래에 코드 추가
        result = oauthService.getUserInfo(access_Token);

        return result;
    }
}
