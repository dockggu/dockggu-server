package com.DXsprint.dockggu.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto {
    private Long partyId;
    private String partyName;
    private String partyIntro;
    private Long partyMaster;
    private String partyCategori;
    private String partyUserNum;
    private String partyUserMaxnum;
    private String partyLink;
    private String partyProfileImgName;
    private String partyProfileImgPath;
}
