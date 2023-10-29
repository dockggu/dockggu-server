package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.BookertonEntity;
import com.DXsprint.dockggu.entity.MybookEntity;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.BookertonRepository;
import com.DXsprint.dockggu.repository.MybookRepository;
import com.DXsprint.dockggu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookertonService {

    @Autowired
    BookertonRepository bookertonRepository;
    @Autowired
    private MybookRepository mybookRepository;

    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Bookerton 생성 - partyId
     * @param dto
     * @return
     */
    @Transactional
    public ResponseDto<BookertonEntity> createBookerton(BookertonDto dto) {
        System.out.println("BookertonService.createBookerton");

        LocalDateTime localNow = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMDDhh24mmss");
        String now = localNow.format(dateTimeFormatter);

        dto.setBookertonCreationTime(now);
        dto.setBookertonStatus("A");
        dto.setBookertonUserNum(1);

        BookertonEntity bookertonEntity = new BookertonEntity(dto);
        System.out.println("Bookerton : " + bookertonEntity.toString());
        try {
            bookertonRepository.save(bookertonEntity);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to create bookerton",null);
    }

    // Party에서 진행 중(bookerton_state = 'A')인 Bookerton List를 조회
    @Transactional(readOnly = true)
    public ResponseDto<List<BookertonEntity>> getBookertonList(Long party,int page) {
        System.out.println("BookertonService.getBookertonList");

        List<BookertonEntity> result = null;

        try {
            result = bookertonRepository.findAllByPartyIdAndBookertonStatus(party, "A");
        } catch (Exception e) {
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get BookertonList", result);
    }

    /**
     * Bookerton 참여하기 - tb_mybook 저장
     * @param mybookDto
     * @return
     */
    @Transactional
    public ResponseDto<?> createMybook(MybookDto mybookDto, String userId) {
        System.out.println(">>> BookertonService.createMybook");
        MybookEntity mybookEntity = new MybookEntity(mybookDto);

        // userId 세팅
        mybookEntity.setUserId(Long.parseLong(userId));

        // 책 이미지 업로드 - 알라딘 API에 없어서 다른 방법으로 가져와야할듯 의논해보기

        try {
            mybookRepository.save(mybookEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to participant in bookerton", null);

    }


    /**
     * Page Update 하기 - 조회 후 저장
     * @param mybookDto
     * @return
     */
    @Transactional
    public ResponseDto<?> updateMybook(MybookDto mybookDto) {
        System.out.println(">>> BookertonService.updateMybook");

        try {
            MybookEntity mybookEntity = mybookRepository.findById(mybookDto.getBookId()).orElse(null);
            mybookEntity.setBookReadPage(mybookDto.getBookReadPage());
            mybookRepository.save(mybookEntity);            // 다시 저장하여 반영
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB Error");
        }

        return ResponseDto.setSuccess("Success to update page", null);
    }

    /**
     * Bookerton 참여자 리스트 조회 서비스 ( 유저정보 및 책 정보 )
     * @param bookertonId
     * @return
     * warn - userIdList 이상한데
     */
    @Transactional
    public ResponseDto<?> getBookertonUserList(String bookertonId) {
        System.out.println(">>>BookertonService.getBookertonUserList");

        BookertonUserListResponseDto result = new BookertonUserListResponseDto();
        List<UserEntity> userEntityList = null;
        List<MybookEntity> mybookEntityList = null;
        List<Long> userIdList = new ArrayList<>();
        MybookDto mybookDto = new MybookDto();
        UserDto userDto = new UserDto();
        List<MybookDto> mybookDtoList = null;
        List<UserDto> userDtoList = null;

        try {
            mybookEntityList = mybookRepository.findByBookertonId(Long.parseLong(bookertonId));

            for(Long userId : userIdList) {
                userIdList.add(userId);
            }
            System.out.println("userIdList : " + userIdList.toString());

            userEntityList = userRepository.findByUserIdIn(userIdList);

            userDtoList = userEntityList.stream()
                    .map(userInfo -> {
                        userDto.setUserId(userInfo.getUserId());
                        userDto.setUserNickname(userInfo.getUserNickname());
                        userDto.setUserProfileImgName(userInfo.getUserProfileImgName());
                        userDto.setUserProfileImgPath(userInfo.getUserProfileImgPath());

                        return userDto;
                    }).collect(Collectors.toList());

            mybookDtoList = mybookEntityList.stream()
                    .map(mybookInfo -> {
                        mybookDto.setBookertonId(mybookInfo.getBookertonId());
                        mybookDto.setUserId(mybookInfo.getUserId());
                        mybookDto.setBookName(mybookInfo.getBookName());
                        mybookDto.setBookAuthor(mybookInfo.getBookAuthor());
                        mybookDto.setBookPublisher(mybookInfo.getBookPublisher());
                        mybookDto.setBookReadPage(mybookInfo.getBookReadPage());
                        mybookDto.setBookTotalPage(mybookInfo.getBookTotalPage());
                        mybookDto.setBookImgName(mybookInfo.getBookImgName());
                        mybookDto.setBookImgPath(mybookInfo.getBookImgPath());

                        return mybookDto;
                    }).collect(Collectors.toList());

            result.setUserListResponseDtoList(userDtoList);
            result.setMybookDtoList(mybookDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB Error");
        }


        return ResponseDto.setSuccess("Success to get user list", null);
    }


    /**
     * Bookerton 마감 시 award + 1
     * @param request
     * @return
     */
    @Transactional
    public ResponseDto<?> updateBookertonAward(Map<String, List<String>> request) {
        System.out.println(">>> BookertonService.updateBookertonAward");

        List<String> userIdList = request.get("userIdList");

        // Id String -> Long 타입 변환
        List<Long> userIdsAsLong = userIdList.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // userIdsAsLong에는 Long 타입의 값이 포함된 리스트가 저장됨
        for (Long userId : userIdsAsLong) {
            System.out.println(userId);
        }

        try {
            // 조회 후 award + 1 update
            List<UserEntity> userEntityList = userRepository.findByUserIdIn(userIdsAsLong);

            for (UserEntity userEntity : userEntityList) {
                userEntity.setUserAward(userEntity.getUserAward() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DB Error");
        }


        return ResponseDto.setSuccess("Success to update bookerton award!", null);
    }
}
