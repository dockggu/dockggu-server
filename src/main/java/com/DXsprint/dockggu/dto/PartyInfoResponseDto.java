package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyInfoResponseDto {
    private PartyResponseDto partyResponseDto;
    private List<PartyUserListResponseDto> partyUserListResponseDto;
}
