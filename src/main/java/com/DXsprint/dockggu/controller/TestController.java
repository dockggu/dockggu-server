package com.DXsprint.dockggu.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @PostMapping("/")
    public String testPost() {

        return "result";
    }

    @GetMapping("/")
    public String testGet() {

        return "result";
    }

    @PostMapping("/status")
    public String status() {

        return "status";
    }
}
