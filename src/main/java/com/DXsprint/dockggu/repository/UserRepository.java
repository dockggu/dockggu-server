package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    boolean existsByUserEmail(String userEmail);

    // 로그인 후 정보 받기
    UserEntity findByUserEmailAndUserPassword(String userEmail, String userPassword);

    // 로그인을 위한 ID/PASSWORD 확인
    //boolean existsByUserEmailAndUserPassword(String userEmail, String userPassword);

    public UserEntity findByUserEmail(String userEmail);
}

