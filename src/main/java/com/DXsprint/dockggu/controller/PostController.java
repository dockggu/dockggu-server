package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PostEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @GetMapping("/")
    public String getPost(@AuthenticationPrincipal String userEmail) {

        return "사용자 : " + userEmail;
    }

    @GetMapping("/top3")
    public ResponseDto<List<PostEntity>> getTop3() {


        return null;
    }

    @GetMapping("/list")
    public ResponseDto<List<PostEntity>> getList() {

        return null;
    }

}
