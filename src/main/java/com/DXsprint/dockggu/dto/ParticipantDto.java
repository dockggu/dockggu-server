package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long participantId;
    private Long userId;
    private Long partyId;
    private String participantState;
}
