package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.BookertonDto;
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

    @PostMapping("/create")
    public ResponseDto<?> createBookerton(@RequestBody BookertonDto dto) {
        System.out.println("BookertonController.createBookerton");

        ResponseDto<BookertonEntity> result = bookertonService.createBookerton(dto);

        return result;
    }

    @GetMapping("/list")
    public ResponseDto<?> getBookertonList(@RequestParam Long partyId, @RequestParam int page) {
        System.out.println("BookertonController.getBookertonList");

        ResponseDto<?> result = bookertonService.getBookertonList(partyId, page);

        return result;
    }
}
