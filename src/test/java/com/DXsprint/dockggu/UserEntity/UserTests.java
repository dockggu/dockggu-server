package com.DXsprint.dockggu.UserEntity;

import static org.assertj.core.api.Assertions.assertThat;

import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTests {
//    @Autowired
//    UserRepository userRepository;
////https://congsong.tistory.com/51
//    @Test
//    void save() {
//        // 유저 파라미터 생성
//        UserEntity params = UserEntity.builder()
//                .userEmail("1@1")
//                .userPassword("1")
//                .userNickname("1n")
//                .build();
//
//        // 저장
//        userRepository.save(params);
//
//        // 조회
//        UserEntity entity = userRepository.findById("?").get();
//        assertThat(entity.getUserEmail()).isEqualTo("1@1");
//        assertThat(entity.getUserNickname()).isEqualTo("1");
//        assertThat(entity.getUserPassword()).isEqualTo("1");
//
//    }
//
//    @Test
//    void findAll() {
//        UserEntity entity = userRepository.findById("1").get();
//
//        userRepository.delete(entity);
//    }
}
