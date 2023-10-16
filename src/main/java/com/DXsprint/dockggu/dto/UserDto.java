package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String userEmail;
    private String userKakaoEmail;
    private String userNickname;
    private Integer userAward;
    private String userProfileImgName;
    private String userProfileImgPath;
}
