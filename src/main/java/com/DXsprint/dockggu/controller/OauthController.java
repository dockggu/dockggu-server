package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Autowired OauthService oauthService;

    @GetMapping("/{registrationId}")
    public ResponseDto<?> socialLogin(@RequestParam String code, @PathVariable String registrationId) {
        System.out.println(">>>OauthController.socialLogin");
        System.out.println("registrationId : " + registrationId);
        System.out.println("code : " + code);
        ResponseDto<?> result = oauthService.socialLogin(code, registrationId);

        return result;
    }
}
