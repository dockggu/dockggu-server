package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookertonUserListResponseDto {
    private List<UserDto> userListResponseDtoList;
    private List<MybookDto> mybookDtoList;
}
