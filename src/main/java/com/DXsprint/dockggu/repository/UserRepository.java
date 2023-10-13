package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    boolean existsByUserEmail(String userEmail);

    // 로그인 후 정보 받기
    UserEntity findByUserEmailAndUserPassword(String userEmail, String userPassword);

    // 로그인을 위한 ID/PASSWORD 확인
    //boolean existsByUserEmailAndUserPassword(String userEmail, String userPassword);

    UserEntity findByUserId(Long userId);

    UserEntity findByUserEmail(String userEmail);

    UserEntity findByUserKakaoEmail(String userKakaoEmail);

    List<UserEntity> findByUserIdIn(List<Long> userIdList);


}

