package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.FileEntity;
import com.DXsprint.dockggu.entity.MybookEntity;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.AwardRepository;
import com.DXsprint.dockggu.repository.MybookRepository;
import com.DXsprint.dockggu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    FileService fileService;


    /**
     * Mypage 기본 정보 조회
     * @param userId
     * @return
     */
    @Transactional
    public ResponseDto<?> getUserInfo(String userId) {
        System.out.println(">>> MypageService.getUserInfo");

        MypageResponseDto mypageResponseDto = new MypageResponseDto();
        // 응답을 위한 Dto 생성
        UserDto userDto = new UserDto();
        List<MybookDto> mybookDtoList = new ArrayList<>();
        MybookDto mybookDto = new MybookDto();


        UserEntity userEntity = new UserEntity();
        List<MybookEntity> mybookEntityList = new ArrayList<>();


        try {
            // 사용자가 읽은 책 List 조회 / 사용자 정보 / 수상 정보 조회
            mybookEntityList = mybookRepository.findByUserId(Long.parseLong(userId));
            userEntity = userRepository.findByUserId(Long.parseLong(userId));

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
            userDto.setUserEmail(userEntity.getUserEmail());
            userDto.setUserNickname(userEntity.getUserNickname());
            userDto.setUserAward(userEntity.getUserAward());
            userDto.setUserProfileImgPath(userEntity.getUserProfileImgPath());
            userDto.setUserProfileImgName(userEntity.getUserProfileImgName());

            mypageResponseDto.setUserDto(userDto);
            mypageResponseDto.setMybookDtoList(mybookDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("Error");
        }


        return ResponseDto.setSuccess("Success to get userInfo", mypageResponseDto);
    }


    /**
     * Mypage 이미지 수정
     * @param userId
     * @param imgFile
     * @return
     */
    @Transactional
    public ResponseDto<?> updateUserInfo(String userId, MultipartFile[] imgFile) {
        System.out.println(">>> MypageService.updateUserInfo");
        UserEntity userEntity = new UserEntity();

        // 파일 업로드를 위한 세팅
        FileEntity fileInfo = new FileEntity();

        try {
            userEntity = userRepository.findByUserId(Long.parseLong(userId));

            if(!imgFile[0].isEmpty()) {
                fileInfo = fileService.uploadFile(imgFile);
            }

            // 이미지 정보 업데이트
            userEntity.setUserProfileImgName(fileInfo.getFileName());
            userEntity.setUserProfileImgPath(fileInfo.getFileUrl());
            userRepository.save(userEntity);

            System.out.println("userEntity >>> " + userEntity.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Error!");
        }

        return ResponseDto.setSuccess("Success to set userInfo", null);
    }
}
