package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.PartyDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.service.FileService;
import com.DXsprint.dockggu.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseDto<?> createParty(@RequestParam String partyName,
                                      @RequestParam String partyIntro,
                                      @RequestParam String partyCategory,
                                      @RequestParam String partyUserMaxnum,
                                      @RequestParam String userId,
                                      @RequestParam MultipartFile[] imgFile) throws Exception {
        System.out.println("PartyController.createParty ===");

        PartyDto partyDto = new PartyDto();
        partyDto.setPartyName(partyName);
        partyDto.setPartyIntro(partyIntro);
        partyDto.setPartyCategory(partyCategory);
        partyDto.setPartyUserMaxnum(partyUserMaxnum);

        ResponseDto<?> result = null;
        result = partyService.createParty(partyDto, userId, imgFile);

        return ResponseDto.setSuccess("Let's go party!", result);
    }
}
