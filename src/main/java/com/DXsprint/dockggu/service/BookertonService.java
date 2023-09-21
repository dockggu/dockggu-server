package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.BookertonDto;
import com.DXsprint.dockggu.dto.BookertonResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.BookertonEntity;
import com.DXsprint.dockggu.repository.BookertonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class BookertonService {

    @Autowired
    BookertonRepository bookertonRepository;

    @Transactional
    public ResponseDto<BookertonEntity> createBookerton(BookertonDto dto) {
        System.out.println("BookertonService.createBookerton");

        LocalDateTime localNow = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMDDhh24mmss");
        String now = localNow.format(dateTimeFormatter);

        dto.setBookertonCreationTime(now);
        dto.setBookertonStatus("A");
        dto.setBookertonUserNum(1);

        BookertonEntity bookertonEntity = new BookertonEntity(dto);

        System.out.println(bookertonEntity.getPartyId());
        System.out.println(bookertonEntity.getBookertonName());
        System.out.println(bookertonEntity.getBookertonStartDate());
        System.out.println(bookertonEntity.getBookertonEndDate());
        System.out.println(bookertonEntity.getBookertonUserNum());
        System.out.println(bookertonEntity.getBookertonUserMaxnum());
        System.out.println(bookertonEntity.getBookertonCreationTime());

        try {
            bookertonRepository.save(bookertonEntity);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to create ookerton",null);
    }

    // Party에서 진행 중(bookerton_state = 'A')인 Bookerton List를 조회
    @Transactional(readOnly = true)
    public ResponseDto<List<BookertonEntity>> getBookertonList(Long party,int page) {
        System.out.println("BookertonService.getBookertonList");

        List<BookertonEntity> result = null;

        try {
            result = bookertonRepository.findAllByPartyIdAndBookertonStatus(party, "A");
        } catch (Exception e) {
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get BookertonList", result);
    }
}
