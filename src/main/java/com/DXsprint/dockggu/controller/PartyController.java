package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.CategoryDto;
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

    /**
     * 카테고리로 Party List 조회하기 (Search 기능 포함 해야함) - 이따 통합하기
     * @param categoryDto
     * @return
     */
    @PostMapping("/category")
    public ResponseDto<List<PartyEntity>> getPartyListCategory(
            @RequestBody CategoryDto categoryDto) {
        System.out.println("MainController.getPartyListCategory");

        // main - 파티 리스트 검색으로 조회.
        ResponseDto<List<PartyEntity>> result = partyService.getPartyListCategory(categoryDto);

        return result;
    }

    @GetMapping("/search")
    public ResponseDto<List<PartyEntity>> getPartyListSearch(@RequestParam String page, @RequestParam String partyName) {
        System.out.println("MainController.getPartyListSearch");

        ResponseDto<List<PartyEntity>> result = partyService.getPartyListSearch(page, partyName);

        return result;
    }

    /**
     * Party 클릭 시 Party 정보 조회 - 현재 가입 중인 회원 프로필 이미지 리스트도 조회해야함
     * @param partyId
     * @return
     */
    @PostMapping("/partyInfo")
    public ResponseDto<?> getPartyInfo(@RequestParam String partyId) {
        System.out.println(">>>PartyController.getPartyInfo");

        ResponseDto<?> result = partyService.getPartyInfo(partyId);

        return result;
    }
}
