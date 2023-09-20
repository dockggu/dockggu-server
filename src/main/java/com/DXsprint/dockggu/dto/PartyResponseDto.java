package com.DXsprint.dockggu.dto;

import com.DXsprint.dockggu.entity.PartyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyResponseDto {
    private Long partyId;
    private String partyName;
    private String partyIntro;
    private Long partyMaster;
    private String partyCategory;
    private String partyUserNum;
    private String partyUserMaxnum;
    private LocalDateTime partyCreationDate;
    private String partyLink;
    private String partyProfileImgName;
    private String partyProfileImgPath;
}

