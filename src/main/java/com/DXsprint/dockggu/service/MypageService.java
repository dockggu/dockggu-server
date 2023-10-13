package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.AwardEntity;
import com.DXsprint.dockggu.entity.MybookEntity;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.AwardRepository;
import com.DXsprint.dockggu.repository.MybookRepository;
import com.DXsprint.dockggu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MypageService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    MybookRepository mybookRepository;

    public ResponseDto<?> getUserInfo(String userId) {
        System.out.println(">>> MypageService.getUserInfo");

        MypageResponseDto mypageResponseDto = new MypageResponseDto();
        // 응답을 위한 Dto 생성
        UserDto userDto = new UserDto();
        AwardDto awardDto = new AwardDto();
        List<MybookDto> mybookDtoList = new ArrayList<>();
        MybookDto mybookDto = new MybookDto();


        UserEntity userEntity = new UserEntity();
        AwardEntity awardEntity = new AwardEntity();
        List<MybookEntity> mybookEntityList = new ArrayList<>();


        try {
            // 사용자가 읽은 책 List 조회 / 사용자 정보 / 수상 정보 조회
            mybookEntityList = mybookRepository.findByUserId(Long.parseLong(userId));
            userEntity = userRepository.findByUserId(Long.parseLong(userId));
            awardEntity = awardRepository.findByUserId(Long.parseLong(userId));

            // mybook list를 mybookDto에 담기
            mybookDtoList = mybookEntityList.stream()
                    .map(mybookInfo -> {
                        mybookDto.setBookId(mybookInfo.getBookId());
                        mybookDto.setBookName(mybookInfo.getBookName());
                        mybookDto.setBookAuthor(mybookInfo.getBookAuthor());
                        mybookDto.setBookPublisher(mybookInfo.getBookPublisher());
                        mybookDto.setBookImgName(mybookInfo.getBookImgName());
                        mybookDto.setBookImgPath(mybookInfo.getBookImgPath());

                        return mybookDto;
                    }).collect(Collectors.toList());

            userDto.setUserId(userEntity.getUserId());
            userDto.setUserNickname(userEntity.getUserNickname());
            userDto.setUserProfileImgPath(userEntity.getUserProfileImgPath());
            userDto.setUserProfileImgName(userEntity.getUserProfileImgName());

            awardDto.setAwardId(awardEntity.getAwardId());
            awardDto.setAwardGold(awardEntity.getAwardGold());
            awardDto.setAwardSilver(awardEntity.getAwardSilver());
            awardDto.setAwardBronze(awardEntity.getAwardBronze());

            mypageResponseDto.setUserDto(userDto);
            mypageResponseDto.setAwardDto(awardDto);
            mypageResponseDto.setMybookDtoList(mybookDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("Error");
        }


        return ResponseDto.setSuccess("Success to get userInfo", mypageResponseDto);
    }

    public ResponseDto<?> setUserInfo(PatchUserDto userInfo, String userId) {


        return ResponseDto.setSuccess("Success to set userInfo", null);
    }
}
