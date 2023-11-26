package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.dto.MybookDto;
import com.DXsprint.dockggu.entity.MybookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MybookRepository extends JpaRepository<MybookEntity, Long> {
    @Query("SELECT m FROM tb_mybook m WHERE m.bookertonId = :bookertonId ORDER BY m.userId ASC")
    List<MybookEntity> findByBookertonIdOrderByUserIdAsc(@Param("bookertonId") Long bookertonId);

    // Mypage - user가 읽은 책 정보
    List<MybookEntity> findByUserId(Long userId);

    List<MybookEntity> findUserIdByBookertonId(Long bookertonId);
    MybookEntity findBookIdByUserIdAndBookertonId(Long userId, Long bookertonId);

    boolean existsByUserIdAndBookertonId(Long userId, Long bookertonId);

    @Modifying
    @Query("DELETE FROM tb_mybook p WHERE p.userId = :userId")
    void deleteByUserId(Long userId);
}
