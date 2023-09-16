package com.DXsprint.dockggu.dto;

import com.DXsprint.dockggu.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    // controller -> view 로 데이터를 응답해주는 DTO
    private String token;
    private int exprTime;
    private UserEntity user;    // 원래는 반환할 email과 password만 지정해도 상관엄써
}
