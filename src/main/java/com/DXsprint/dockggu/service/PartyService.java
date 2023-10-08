package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.FileEntity;
import com.DXsprint.dockggu.entity.ParticipantEntity;
import com.DXsprint.dockggu.repository.ParticipantRepository;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    /**
     * party 생성
     * @param dto
     * @param userId
     * @param imgFile
     * @return
     */
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

    @Transactional
    public ResponseDto<?> getPartyInfo(String partyId) {
        System.out.println(">>> PartyService.getPartyInfo");

        PartyEntity partyEntity = null;
        try {
             partyEntity = partyRepository.findByPartyId(Long.parseLong(partyId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DB Error");
        }

        PartyResponseDto partyResponseDto = new PartyResponseDto();

        partyResponseDto.setPartyId(partyEntity.getPartyId());
        partyResponseDto.setPartyName(partyEntity.getPartyName());
        partyResponseDto.setPartyIntro(partyEntity.getPartyIntro());
        partyResponseDto.setPartyMaster(partyEntity.getPartyMaster());
        partyResponseDto.setPartyCategory(partyEntity.getPartyCategory());
        partyResponseDto.setPartyUserNum(partyEntity.getPartyUserNum());
        partyResponseDto.setPartyUserMaxnum(partyEntity.getPartyUserMaxnum());
        partyResponseDto.setPartyProfileImgName(partyEntity.getPartyProfileImgName());
        partyResponseDto.setPartyProfileImgPath(partyEntity.getPartyProfileImgPath());

        return ResponseDto.setSuccess("Success to get PartyInfo", partyResponseDto);
    }

    /**
     * main page에서 party List 조회
     * @param categoryDto
     * @return
     */
    @Transactional(readOnly = true)
    public ResponseDto<List<PartyEntity>> getPartyListCategory(CategoryDto categoryDto) {
        System.out.println("MainService.getPartyListCategory");

        List<PartyEntity> result = null;
        int pageNum = Integer.parseInt(categoryDto.getPage());
        System.out.println("start Page : " + pageNum);

        PageRequest pageable = PageRequest.of(pageNum, 30);
        List<String> categories = categoryDto.getCategories();
        try {
            for(int i=0; i<5; i++) {
                System.out.println("카테고리 : " + categories.get(i));
            }
//            result = partyRepository.findByPartyCategoryInOrderByPartyCreationDateDesc(categories, pageable);
            result = partyRepository.findByPartyCategoryInOrderByPartyCreationDateDesc(categories);

            if(categories.get(0).equals("bc0000"))
                result = partyRepository.findAllByOrderByPartyCreationDateDesc(pageable);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get list!", result);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<PartyEntity>> getPartyListSearch(String page, String partyName) {
        System.out.println("MainService.getPartyListSearch");

        List<PartyEntity> result = null;

        try {
            result = partyRepository.findByPartyNameContainingOrderByPartyCreationDateDesc(partyName);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get list!", result);
    }
}
