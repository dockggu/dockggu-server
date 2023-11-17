package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.BookertonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

public interface BookertonRepository extends JpaRepository<BookertonEntity, Long> {
    List<BookertonEntity> findAllByPartyId(Long partyId);

    BookertonEntity findByBookertonId(Long bookertonId);

//    List<BookertonEntity> findAllByIfBookertonEndDateBefore(String currentDate);

    @Modifying
    @Query(value = "UPDATE tb_bookerton b set b.bookerton_status = 'P' where b.bookerton_end_date < :currentDate", nativeQuery = true)
    void updateBookertonStatusIfBookertonEndDateBefore(@Param("currentDate")String currentDate) throws Exception;

}
