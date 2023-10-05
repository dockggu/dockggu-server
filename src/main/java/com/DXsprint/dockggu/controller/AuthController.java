package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.SignInDto;
import com.DXsprint.dockggu.dto.SignUpDto;
import com.DXsprint.dockggu.dto.SignInResponseDto;
import com.DXsprint.dockggu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired AuthService authService;
    //@Autowired KakaoService kakaoService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        System.out.println("AuthController.signUp");

        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody) {
        System.out.println("AuthController.signIn");

        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

//    @RequestMapping("kakao/sign_in")
//    public String kakaoSignIn(@RequestParam("code") String code) {
//        Map<String,Object> result = kakaoService.execKakaoLogin(code);
//        return "redirect:webauthcallback://success?customToken="+result.get("customToken").toString();
//    }
}
