package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.AwardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<AwardEntity, Long> {
    AwardEntity findByUserId(Long userId);
}
