package com.DXsprint.dockggu.entity;

import com.DXsprint.dockggu.dto.BookertonDto;
import com.DXsprint.dockggu.global.JpaAuditing.entity.BaseEntity;
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
@Entity(name = "bookerton")
@Table(name = "tb_bookerton")
public class BookertonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookertonId;
    private Long partyId;
    private Long userId;
    private String bookertonName;
    private String bookertonStartDate;
    private String bookertonEndDate;
    private int bookertonUserNum;
    private int bookertonUserMaxnum;
    private String bookertonStatus;
    private String bookertonCreationTime;

    public BookertonEntity(BookertonDto dto) {
        this.partyId = dto.getPartyId();
        this.userId = dto.getUserId();
        this.bookertonName = dto.getBookertonName();
        this.bookertonStartDate = dto.getBookertonStartDate();
        this.bookertonEndDate = dto.getBookertonEndDate();
        this.bookertonEndDate = dto.getBookertonEndDate();
        this.bookertonUserNum = dto.getBookertonUserNum();
        this.bookertonUserMaxnum = dto.getBookertonUserMaxnum();
        this.bookertonStatus = dto.getBookertonStatus();
        this.bookertonCreationTime = dto.getBookertonCreationTime();
    }
}
