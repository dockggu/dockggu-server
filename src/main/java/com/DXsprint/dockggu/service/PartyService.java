package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.PartyDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.ParticipantEntity;
import com.DXsprint.dockggu.repository.ParticipantRepository;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private ParticipantRepository participantRepository;

//    public ResponseDto<List<PartyDto>> getPartyList(int n, int m) {
//        List<PartyEntity> parties = partyRepository.findAllByOrderByObjectCreateDateAsc();
//
//        int fromIndex = Math.min(n, parties.size());
//        int toIndex = Math.min(m, parties.size());
//
//        if (fromIndex <= toIndex) {
//            return parties.subList(fromIndex, toIndex);
//        } else {
//            return Collections.emptyList();
//        }
//    }

    public ResponseDto<?> createParty(PartyDto dto, String userId) {
        System.out.println("PartyService.createParty ===");

        System.out.println("userId : " + userId);
        String partyName = dto.getPartyName();
        Long user = Long.parseLong(userId);
        try {
            // Party 명 존재 하는 경우
            if(partyRepository.existsByPartyName(partyName)) {
                return ResponseDto.setFailed("The name already exists");
            }

            // Party 생성 - userEmail : 파티장
            dto.setPartyMaster(user);
            PartyEntity partyEntity = new PartyEntity(dto);
            partyEntity.setPartyUserNum("1");
            partyRepository.save(partyEntity);

            // 파티 명으로 파티 ID 찾기
            partyEntity = partyRepository.findByPartyName(dto.getPartyName());
            Long partyId = partyEntity.getPartyId();

            // Participant 명단 추가
            ParticipantEntity participantEntity = new ParticipantEntity();
            participantEntity.setUserId(user);
            participantEntity.setPartyId(partyId);
            participantEntity.setParticipantState("A");
            participantRepository.save(participantEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success To Make Party!", null);
    }
}
