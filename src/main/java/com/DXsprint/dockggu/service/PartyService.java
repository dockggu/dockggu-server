package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.FileEntity;
import com.DXsprint.dockggu.entity.ParticipantEntity;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.ParticipantRepository;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import com.DXsprint.dockggu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;

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


    /**
     * Party Info - 파티 클릭 시 파티 정보 보여줌
     * @param partyId
     * @return
     */
    @Transactional
    public ResponseDto<?> getPartyInfo(String partyId) {
        System.out.println(">>> PartyService.getPartyInfo");


        PartyEntity partyEntity = null;     // party 정보 담기
        List<UserEntity> userEntityList = null;
        List<Long> userIdList = new ArrayList<>();
        PartyInfoResponseDto partyInfoResponseDto = new PartyInfoResponseDto();
        List<PartyUserListResponseDto> partyUserListResponseDtoList = null;

        try {
            // 파티 정보 조회
            partyEntity = partyRepository.findByPartyId(Long.parseLong(partyId));
            System.out.println("partyId : " + partyId);
            // 파티 속한 유저ID 리스트 생성
            List<ParticipantEntity> results = participantRepository.findUserIdsByPartyId(Long.parseLong(partyId));

            for (ParticipantEntity result : results) {
                userIdList.add(result.getUserId());
            }

            System.out.println("userIdList : " + userIdList.toString());
            // 파티에 속한 유저 정보 리스트 조회
            userEntityList = userRepository.findByUserIdIn(userIdList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DB Error");
        }
        // party 정보
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

        System.out.println("partyResponseDto : " + partyResponseDto.toString());
        // 파티에 속한 유저 List (id, nickname, img)
        partyUserListResponseDtoList = userEntityList.stream()
                .map(userInfo -> {
                    PartyUserListResponseDto partyUserListResponseDto = new PartyUserListResponseDto();
                    partyUserListResponseDto.setUserId(userInfo.getUserId());
                    partyUserListResponseDto.setUserNickname(userInfo.getUserNickname());
                    partyUserListResponseDto.setFileName(userInfo.getUserProfileImgName());
                    partyUserListResponseDto.setFileUrl(userInfo.getUserProfileImgPath());
                    return partyUserListResponseDto;
                }).collect(Collectors.toList());

        // 결과 값 저장
        partyInfoResponseDto.setPartyResponseDto(partyResponseDto);
        partyInfoResponseDto.setPartyUserListResponseDto(partyUserListResponseDtoList);

        return ResponseDto.setSuccess("Success to get PartyInfo", partyInfoResponseDto);
    }


    /**
     * Party 참여
     * @param participantDto
     * @return
     */
    @Transactional
    public ResponseDto<?> insertParticipant(ParticipantDto participantDto) {
        System.out.println(">>> PartySerive.insertParticipant");

        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setUserId(participantDto.getUserId());
        participantEntity.setPartyId(participantDto.getPartyId());
        participantEntity.setParticipantState("A");
        participantRepository.save(participantEntity);

        return ResponseDto.setSuccess("Success to participant!", null);
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

    /**
     * 파티 검색 시 파티 리스트 조회
     * @param page
     * @param partyName
     * @return
     */
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


    /**
     * 내가 가입한 파티 리스트 조회
     * @param userId
     * @return
     */
    @Transactional
    public ResponseDto<List<PartyDto>> getMyPartyList(String userId) {
        System.out.println(">>>PartyService.getMyPartyList");

        List<PartyDto> partyInfoResponseDtoList = new ArrayList<>();
        List<Long> partyIdList = new ArrayList<>();
        List<PartyEntity> partyEntityList = new ArrayList<>();
        PartyDto partyDto = new PartyDto();

        try {
            // 나의 파티 ID ㅣList 조회
            partyIdList = participantRepository.findPartyIdsByUserId(Long.parseLong(userId));
            System.out.println("PartyIdList >>> " + partyIdList.toString());
            // Party ID List로 각각 party 정보 가져오기
            partyEntityList = partyRepository.findByPartyIdIn(partyIdList);

            partyInfoResponseDtoList = partyEntityList.stream()
                    .map(partyInfo -> {
                        partyDto.setPartyId(partyInfo.getPartyId());
                        partyDto.setPartyName(partyInfo.getPartyName());
                        partyDto.setPartyIntro(partyInfo.getPartyIntro());
                        partyDto.setPartyCategory(partyInfo.getPartyCategory());
                        partyDto.setPartyUserNum(partyInfo.getPartyUserNum());
                        partyDto.setPartyUserMaxnum(partyInfo.getPartyUserMaxnum());
                        partyDto.setPartyProfileImgName(partyInfo.getPartyProfileImgName());
                        partyDto.setPartyProfileImgPath(partyInfo.getPartyProfileImgPath());

                        return partyDto;
                    }).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Error!");
        }

        return ResponseDto.setSuccess("Success to get my parties!", partyInfoResponseDtoList);
    }

}
