package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.SignInDto;
import com.DXsprint.dockggu.dto.SignInResponseDto;
import com.DXsprint.dockggu.dto.SignUpDto;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.UserRepository;
import com.DXsprint.dockggu.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired UserRepository userRepository;
    @Autowired TokenProvider tokenProvider;


    // ?
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public ResponseDto<?> signUp(SignUpDto dto) {
        System.out.println("AuthService.signUp() ==============");
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        String userPasswordCheck = dto.getUserPasswordCheck();
        String userNickname = dto.getUserNickname();

        // email 중복 확인 - existById : JPA에서 지원해주는거
        // DB 접근하는 친구는 try - catch문 써주자
        try {
            System.out.println("아이디 중복 탐색 - start : " + userEmail);
            if (userRepository.existsByUserEmail(userEmail)) {
                System.out.println("1");
                return ResponseDto.setFailed("Existed Email");
            } else {
                System.out.println("2");
            }
            System.out.println("finish" + userEmail);
        } catch (Exception e) {
            System.out.println("err : " + userEmail);
            return ResponseDto.setFailed("DB Error");
        }

        // 비밀번호확인 일치 여부 check
        if (!userPassword.equals(userPasswordCheck)) {
            System.out.println("?");
            return ResponseDto.setFailed("password does not matched!");
        }

        // UserEntity 생성
        UserEntity userEntity = new UserEntity(dto);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setUserPassword(encodedPassword);

        // UserRepository 를 이용하여 DB에 Entity 저장
        try {
            System.out.println("아이디 저장");
            userRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("DB Error");
        }
        System.out.println("????????");
        return ResponseDto.setSuccess("SignUp Success", null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
        System.out.println("AuthService.signIn() ==============");

        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        UserEntity userEntity = null;
        System.out.println("userEmail : " + userEmail);
        System.out.println("userPassword : " + userPassword);

        try {
            System.out.println("[ ID / Password ] 일치 여부 확인");
            userEntity = userRepository.findByUserEmail(userEmail);
            System.out.println("? : " + userEntity.getUserPassword());

            // 아이디 존재 여부 확인
            if(userEntity == null) return ResponseDto.setFailed("Sign In Fail");

            // password 불일치
            if(passwordEncoder.matches(userPassword, userEntity.getUserPassword()))
                return ResponseDto.setFailed("Sign In Fail");
        }
        catch (Exception e) {
            return ResponseDto.setFailed("DB error");
        }
        System.out.println("2222");

        String token = tokenProvider.create(userEmail);
        int exprTime = 3600000;
        System.out.println("33333");

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
        return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
    }
}
