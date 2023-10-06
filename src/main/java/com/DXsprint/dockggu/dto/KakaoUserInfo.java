package com.DXsprint.dockggu.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class KakaoUserInfo {
    private String nickname;
    private String profileImgUrl;
//    private String thumnailImgUrl;

//    private String birthday;
//    private boolean hasBirthDay;
//    private String gender;
//    private boolean hasGender;
}