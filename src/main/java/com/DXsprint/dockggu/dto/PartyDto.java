package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto {
    private String party_id;
    private String party_master;
    private String party_name;
    private String party_intro;
    private String party_categori;
    private String party_bookerton;
    private String party_user_num;
    private String party_max_user_num;
    private String party_d_day;
    private String party_start_date;
    private String party_end_date;
}
