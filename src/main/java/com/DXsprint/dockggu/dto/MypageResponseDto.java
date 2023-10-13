package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {
    private UserDto userDto;
    private AwardDto awardDto;
    private List<MybookDto> mybookDtoList;
}
