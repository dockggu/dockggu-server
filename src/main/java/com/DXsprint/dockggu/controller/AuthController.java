package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.SignInDto;
import com.DXsprint.dockggu.dto.SignUpDto;
import com.DXsprint.dockggu.dto.SignInResponseDto;
import com.DXsprint.dockggu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(
            @RequestParam String userEmail,
            @RequestParam String userPassword,
            @RequestParam String userPasswordCheck,
            @RequestParam String userNickname,
            MultipartFile[] imgFile) {
        System.out.println("AuthController.signUp");

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUserEmail(userEmail);
        signUpDto.setUserPassword(userPassword);
        signUpDto.setUserPasswordCheck(userPasswordCheck);
        signUpDto.setUserNickname(userNickname);

        ResponseDto<?> result = authService.signUp(signUpDto, imgFile);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody) {
        System.out.println("AuthController.signIn");

        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

}
