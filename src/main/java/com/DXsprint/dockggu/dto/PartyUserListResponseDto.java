package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyUserListResponseDto {
    private Long userId;
    private String userNickname;
    private String fileName;
    private String fileUrl;
}
