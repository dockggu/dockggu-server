package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.SignInDto;
import com.DXsprint.dockggu.dto.SignUpDto;
import com.DXsprint.dockggu.dto.SignInResponseDto;
import com.DXsprint.dockggu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        System.out.println(requestBody.toString());

        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<?> signIn(@RequestBody SignInDto requestBody) {
        System.out.println(requestBody.toString());

        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }
}
