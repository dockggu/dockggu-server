package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.PartyDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party")
public class PartyController {
    @Autowired
    PartyService partyService;
    @GetMapping("/list")
    public ResponseDto<List<PartyEntity>> getPartyList () {

        return null;
    }

//     Party 생성
    @PostMapping("/throw")
    public ResponseDto<?> createParty(@RequestBody PartyDto requestBody, @RequestParam String userId) {
        System.out.println("PartyController.createParty ===");

        ResponseDto<?> result = partyService.createParty(requestBody, userId);

        return null;
    }
}
