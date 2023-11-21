package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.UserDto;
import com.DXsprint.dockggu.service.MypageService;
import com.DXsprint.dockggu.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * mypage 이미지 수정
     * @param userId
     * @return
     */
    @PostMapping("/updateProfile")
    public ResponseDto<?> updateUserInfo(@AuthenticationPrincipal String userId,
                                         @RequestParam MultipartFile[] imgFile) throws Exception {
        System.out.println(">>> MypageController.updateUserInfo");

        ResponseDto<?> result = mypageService.updateUserInfo(userId, imgFile);

        return result;
    }


    /**
     * 회원 정보 삭제
     * @param userId
     * @return
     */
    @PostMapping("/userDelete")
    public ResponseDto<?> deleteUserInfo(@AuthenticationPrincipal String userId) {
        System.out.println(">>> MypageController.deleteUserInfo");

        ResponseDto<?> result = mypageService.deleteUserInfo(userId);

        return result;
    }
}
