package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookertonDto {
    private Long partyId;
    private Long userId;
    private String bookertonName;
    private String bookertonStartDate;
    private String bookertonEndDate;
    private int bookertonUserNum;
    private int bookertonUserMaxnum;
    private String bookertonStatus;
    private String bookertonCreationTime;
}
