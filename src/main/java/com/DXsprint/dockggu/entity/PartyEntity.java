package com.DXsprint.dockggu.entity;

import com.DXsprint.dockggu.dto.PartyDto;
import com.DXsprint.dockggu.global.JpaAuditing.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Party")
@Table(name = "TB_PARTY")
public class PartyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PrePersist
    public void prePersist() {
        this.partyCreationDate = LocalDateTime.now();
    }

    public PartyEntity(PartyDto dto) {
        this.partyName = dto.getPartyName();
        this.partyIntro = dto.getPartyIntro();
        this.partyMaster = dto.getPartyMaster();
        this.partyCategory = dto.getPartyCategory();
        this.partyUserNum = dto.getPartyUserNum();
        this.partyUserMaxnum = dto.getPartyUserMaxnum();
        this.partyLink = dto.getPartyLink();
        this.partyProfileImgName = dto.getPartyProfileImgName();
        this.partyProfileImgPath = dto.getPartyProfileImgPath();
    }
}
