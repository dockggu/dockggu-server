package com.DXsprint.dockggu.entity;

import com.DXsprint.dockggu.dto.PartyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Party")
@Table(name = "TB_PARTY")
public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public PartyEntity(PartyDto dto) {
        this.partyName = dto.getPartyName();
        this.partyIntro = dto.getPartyIntro();
        this.partyMaster = dto.getPartyMaster();
        this.partyCategori = dto.getPartyCategori();
        this.partyUserNum = dto.getPartyUserNum();
        this.partyUserMaxnum = dto.getPartyUserMaxnum();
        this.partyLink = dto.getPartyLink();
        this.partyProfileImgName = dto.getPartyProfileImgName();
        this.partyProfileImgPath = dto.getPartyProfileImgPath();
    }
}
