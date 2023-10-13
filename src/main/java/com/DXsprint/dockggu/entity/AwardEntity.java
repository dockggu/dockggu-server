package com.DXsprint.dockggu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "award")
@Table(name = "tb_award")
public class AwardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long awardId;
    private Long userId;
    private Long awardGold;
    private Long awardSilver;
    private Long awardBronze;
}
