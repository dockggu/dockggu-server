package com.DXsprint.dockggu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    // RestController - 해당 클래스를 Controller 레이어로 인식 / Rest한 형태로 제작
    // @ResponseBody를 추가해서 Body에 데이터를 담아서 보내주는 식의 통신을 하게따!
    // @RequestMapping(URL 패턴) - Request의 URL 패턴을 보고 해당 클래스를 실행
    @GetMapping("/")
    public String hello() {
        return "Connection Successful";
    }
}
