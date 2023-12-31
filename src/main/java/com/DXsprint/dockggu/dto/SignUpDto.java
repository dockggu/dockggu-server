package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String userEmail;
    private String userSocialEmail;
    private String userPassword;
    private String userPasswordCheck;
    private String userNickname;
    private Integer userAward;

}
