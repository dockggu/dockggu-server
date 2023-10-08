package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.PartyDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.FileResponseDto;
import com.DXsprint.dockggu.entity.FileEntity;
import com.DXsprint.dockggu.entity.ParticipantEntity;
import com.DXsprint.dockggu.repository.ParticipantRepository;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private FileService fileService;

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
    @Transactional
    public ResponseDto<?> createParty(PartyDto dto, String userId, MultipartFile[] imgFile) {
        System.out.println("PartyService.createParty ===");

        System.out.println("userId : " + userId);

        String partyName = dto.getPartyName();
        Long user = Long.parseLong(userId);
        PartyEntity partyEntity = null;


        try {
            // Party 명 존재 하는 경우
            if(partyRepository.existsByPartyName(partyName)) {
                return ResponseDto.setFailed("The name already exists");
            }

            // 파일 업로드
            FileEntity fileInfo = fileService.uploadFile(imgFile);


            // Party 생성 - userEmail : 파티장
            dto.setPartyMaster(user);
            partyEntity = new PartyEntity(dto);
            partyEntity.setPartyUserNum("1");
            partyEntity.setPartyProfileImgName(fileInfo.getFileName());
            partyEntity.setPartyProfileImgPath(fileInfo.getFileUrl());
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

        return ResponseDto.setSuccess("Success To Make Party!", partyEntity.getPartyId());
    }
}
