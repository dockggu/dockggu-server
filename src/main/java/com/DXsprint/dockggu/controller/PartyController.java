package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.PartySearchRequestDto;
import com.DXsprint.dockggu.dto.ParticipantDto;
import com.DXsprint.dockggu.dto.PartyDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * tb_party & tb_participant 통합
 */
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
                                      @RequestParam int partyUserMaxnum,
                                      @AuthenticationPrincipal String userId,
                                      @RequestParam MultipartFile imgFile) throws Exception {
        System.out.println(">>> PartyController.createParty");

        PartyDto partyDto = new PartyDto();
        partyDto.setPartyName(partyName);
        partyDto.setPartyIntro(partyIntro);
        partyDto.setPartyCategory(partyCategory);
        partyDto.setPartyUserMaxnum(partyUserMaxnum);

        ResponseDto<?> result = null;
        result = partyService.createParty(partyDto, userId, imgFile);

        return result;
    }

    /**
     * 카테고리로 Party List 조회하기 (Search 기능 포함 해야함) - 이따 통합하기
     * @param partySearchRequestDto
     * @return
     */
    @PostMapping("/search")
    public ResponseDto<List<PartyEntity>> getPartyListCategory(@RequestBody PartySearchRequestDto partySearchRequestDto) {
        System.out.println("PartyController.getPartyListCategory");

        // main - 파티 리스트 검색으로 조회.
        ResponseDto<List<PartyEntity>> result = partyService.getPartySearch(partySearchRequestDto);

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

    /**
     * 파티 가입 - participant table에 insert (controller 통합)
     * @param participantDto
     * @return
     */
    @PostMapping("/participant")
    public ResponseDto<?> insertParticipant(@RequestBody ParticipantDto participantDto,
                                            @AuthenticationPrincipal String userId) {
        System.out.println(">>> PartyController.insertParticipant");
        ResponseDto<?> result = partyService.insertParticipant(participantDto, userId);

        return result;
    }


    /**
     * 내가 가입한 파티 리스트 조회
     * @param userId
     * @return
     * warn - test 안해봄
     */
    @GetMapping("/partyList")
    public ResponseDto<?> getMyPartyList(@AuthenticationPrincipal String userId) {
        System.out.println(">>> PartyController.getPartyList");
        ResponseDto<?> result = partyService.getMyPartyList(userId);

        return result;
    }
}
