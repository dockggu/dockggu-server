package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.BookertonDto;
import com.DXsprint.dockggu.dto.MybookDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.BookertonEntity;
import com.DXsprint.dockggu.service.BookertonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * tb_bookerton & tb_mybook 통합
 */
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
    public ResponseDto<?> createMybook(@RequestBody MybookDto mybookDto,
                                       @AuthenticationPrincipal String userId) {
        System.out.println(">>> BookertonController.createMybook");

        ResponseDto<?> result = bookertonService.createMybook(mybookDto, userId);

        return result;
    }


    /**
     * mybook page update - 페이지 업데이트
     * @param mybookDto
     * @return
     */
    @PostMapping("/updatePage")
    public ResponseDto<?> updateMybook(@RequestBody MybookDto mybookDto) {
        System.out.println(">>> BookertonController.updateMybook");

        ResponseDto<?> result = bookertonService.updateMybook(mybookDto);

        return result;
    }


    /**
     * Bookerton 참여자 리스트 정보 조회
     * @param bookertonId
     * @return- { userDto, mybookDto }
     */
    @GetMapping("/userList")
    public ResponseDto<?> getBookertonUserList(@RequestParam String bookertonId) {
        System.out.println(">>> BookertonController.getBookertonUserList");

        ResponseDto<?> result = bookertonService.getBookertonUserList(bookertonId);

        return result;
    }

    /**
     * Bookerton 100% 완독자 award + 1
     * @param userIdList
     * @return
     */
    @PostMapping("/award")
    public ResponseDto<?> updateBookertonAward(@RequestBody Map<String, List<String>> userIdList) {
        System.out.println(">>> BookertonController.updateBookertonAward");
        ResponseDto<?> result = bookertonService.updateBookertonAward(userIdList);

        return result;
    }
}
