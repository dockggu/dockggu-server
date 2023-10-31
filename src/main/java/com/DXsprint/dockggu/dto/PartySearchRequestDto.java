package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartySearchRequestDto {
//    private String c1;
//    private String c2;
//    private String c3;
//    private String c4;
//    private String c5;

    private List<String> categories;
    private String page;
    private String partyName;
}
