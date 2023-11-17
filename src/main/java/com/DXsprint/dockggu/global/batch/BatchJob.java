package com.DXsprint.dockggu.global.batch;

import com.DXsprint.dockggu.entity.BookertonEntity;
import com.DXsprint.dockggu.repository.BookertonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchJob {

    @Autowired
    private BookertonRepository bookertonRepository; // BookertonRepository는 JpaRepository를 상속받아야 합니다.

//    @Scheduled(cron = "0 5 0 * * ?") // 매일 00시 05분에 실행
//    @Transactional
//    public void updateBookertonStatus() {
//        LocalDate currentDate = LocalDate.now();
//        LocalTime currentTime = LocalTime.now();
//        LocalTime targetTime = LocalTime.of(0, 5); // 매일 00시 05분
//
//        if (currentTime.isBefore(targetTime)) {
//            // 전날로 계산
//            currentDate = currentDate.minusDays(1);
//        }
//
//        bookertonRepository.updateStatusIfEndDateBeforeOrEqual(currentDate);
//
//    }


    @Scheduled(cron = "0 5 0 * * ?")    // 매일 00시 05분
    @Transactional
    public void updateBookertonStatus() {
        System.out.println(">>> BatchJob.updateBookertonStatus");
        // 현재 날짜
        LocalDate currentDate = LocalDate.now();

        // DateTimeFormatter를 사용하여 yyyyMMdd 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);

        // 결과 출력
        System.out.println("Formatted Date: " + formattedDate);

//        List<BookertonEntity> bookertonEntityList = bookertonRepository.findAllByIfBookertonEndDateBefore(formattedDate);
//        bookertonEntityList.forEach(row -> {
//            row.setBookertonStatus("P");
//        });
        try {
            bookertonRepository.updateBookertonStatusIfBookertonEndDateBefore(formattedDate);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}