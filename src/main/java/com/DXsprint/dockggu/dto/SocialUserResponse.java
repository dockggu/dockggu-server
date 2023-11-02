package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SocialUserResponse {
//    private String id;
    private String email;
    private String nickName;
//    private String gender;
//    private String birthday;
}