package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardDto {
    private Long awardId;
    private Long awardGold;
    private Long awardSilver;
    private Long awardBronze;
}
