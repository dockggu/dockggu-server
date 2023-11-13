package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.dto.MybookDto;
import com.DXsprint.dockggu.entity.MybookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MybookRepository extends JpaRepository<MybookEntity, Long> {
    List<MybookEntity> findByBookertonId(Long bookertonId);

    // Mypage - user가 읽은 책 정보
    List<MybookEntity> findByUserId(Long userId);

    MybookEntity findBookIdByUserIdAndBookertonId(Long userId, Long bookertonId);

    boolean existsByUserIdAndBookertonId(Long userId, Long bookertonId);
}
