package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.PatchUserDto;
import com.DXsprint.dockggu.dto.PatchUserResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    @Autowired
    MypageService mypageService;


    /**
     * Mypage 사용자 정보 조회 - userDto , awardDto, mybooktoList
     * @param userId
     * @return
     */
    @GetMapping("/user")
    public ResponseDto<?> getUserInfo(@AuthenticationPrincipal String userId) {
        System.out.println(">>> MypageController.getUserInfo");
        ResponseDto<?> result = mypageService.getUserInfo(userId);

        return result;
    }

    /**
     * 사용자 정보 수정
     * @param requestBody
     * @param userEmail
     * @return
     */
//    @PatchMapping("/")
//    public ResponseDto<PatchUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody, @AuthenticationPrincipal String userId) {
//        System.out.println(">>>MypageController.patchUser");
//        ResponseDto<?> result = mypageService.setUserInfo(requestBody, userId);
//
//        return result;
//    }
}
