package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.CategoryDto;
import com.DXsprint.dockggu.dto.PartyResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.repository.PartyRepository;
import com.DXsprint.dockggu.service.MainService;
import com.DXsprint.dockggu.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {
    // RestController - 해당 클래스를 Controller 레이어로 인식 / Rest한 형태로 제작
    // @ResponseBody를 추가해서 Body에 데이터를 담아서 보내주는 식의 통신을 하게따!
    // @RequestMapping(URL 패턴) - Request의 URL 패턴을 보고 해당 클래스를 실행

    @Autowired
    MainService mainService;


    @GetMapping("/api/main")
    public String getPartyList() {
        // main - 파티 리스트 카테고리로 조회



        return "";
    }

    @GetMapping("/category")
    public ResponseDto<List<PartyResponseDto>> getPartyListCategory(
            @RequestBody CategoryDto categoryDto,
            @RequestParam String page) {
        System.out.println("MainController.getPartyListCategory");

        // main - 파티 리스트 검색으로 조회.
        int pageNum = Integer.parseInt(page);
        System.out.println("start Page : " + pageNum);
        ResponseDto<List<PartyResponseDto>> result = mainService.getPartyListCategory(categoryDto.getCategories() ,pageNum);

        return result;
    }
}
