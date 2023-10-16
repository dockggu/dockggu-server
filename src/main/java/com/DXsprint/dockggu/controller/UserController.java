package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.PatchUserDto;
import com.DXsprint.dockggu.dto.PatchUserResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    MypageService mypageService;



    @PatchMapping("/")
    public ResponseDto<PatchUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody, @AuthenticationPrincipal String userEmail) {


        return null;
    }
}
