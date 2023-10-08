package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.BookertonDto;
import com.DXsprint.dockggu.dto.MybookDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.BookertonEntity;
import com.DXsprint.dockggu.service.BookertonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookerton")
public class BookertonController {

    @Autowired
    BookertonService bookertonService;

    /**
     * Party장 권한 - Bookerton 생성
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public ResponseDto<?> createBookerton(@RequestBody BookertonDto dto) {
        System.out.println("BookertonController.createBookerton");

        ResponseDto<BookertonEntity> result = bookertonService.createBookerton(dto);

        return result;
    }

    /**
     * 파티의 Bookerton 리스트 조회
     * @param partyId
     * @param page
     * @return
     */
    @GetMapping("/list")
    public ResponseDto<?> getBookertonList(@RequestParam Long partyId, @RequestParam int page) {
        System.out.println("BookertonController.getBookertonList");

        ResponseDto<?> result = bookertonService.getBookertonList(partyId, page);

        return result;
    }

    /**
     * Bookerton 참여를 위한 Mybook 데이터 저장
     * @param mybookDto - { userId, bookertonId, bookName, bookAuthor, bookPublisher, bookTotalPage, bookReadPage}
     * @return
     */
    @PostMapping("/participant")
    public ResponseDto<?> createMybook(@RequestBody MybookDto mybookDto) {
        System.out.println(">>> BookertonController.createMybook");

        ResponseDto<?> result = bookertonService.createMybook(mybookDto);

        return result;
    }
}
