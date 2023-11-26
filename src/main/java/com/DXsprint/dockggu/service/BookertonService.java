package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.*;
import com.DXsprint.dockggu.entity.BookertonEntity;
import com.DXsprint.dockggu.entity.MybookEntity;
import com.DXsprint.dockggu.entity.UserEntity;
import com.DXsprint.dockggu.repository.BookertonRepository;
import com.DXsprint.dockggu.repository.MybookRepository;
import com.DXsprint.dockggu.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.time.LocalDate;
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
        dto.setBookertonUserNum(0);

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

    // Party에서 진행 한 모든 북커톤 보여줌
    // A 됨 P 안됨
    @Transactional(readOnly = true)
    public ResponseDto<List<BookertonEntity>> getBookertonList(Long party,int page) {
        System.out.println("BookertonService.getBookertonList");

        List<BookertonEntity> result = null;

        try {
            result = bookertonRepository.findAllByPartyId(party);
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

        Long bookertonId = mybookDto.getBookertonId();
        String bookImgPath = mybookDto.getBookImgPath();
        mybookEntity.setUserId(Long.parseLong(userId));

        System.out.println("mybookDto : " + mybookDto);

        try {
            String encodedImagePath = URLEncoder.encode(bookImgPath, "UTF-8");
            mybookEntity.setBookImgPath(encodedImagePath);

            if(mybookRepository.existsByUserIdAndBookertonId(Long.parseLong(userId), bookertonId)) {
                return ResponseDto.setFailed("이미 북커톤 신청을 완료했습니다.");
            }
            mybookRepository.save(mybookEntity);

            // Bookerton 참여 인원 1 증가
            BookertonEntity bookertonEntity = bookertonRepository.findByBookertonId(mybookDto.getBookertonId());
            bookertonEntity.setBookertonUserNum(bookertonEntity.getBookertonUserNum()+1);
            bookertonRepository.save(bookertonEntity);

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
    public ResponseDto<?> updateMybook(MybookDto mybookDto, String userId) {
        System.out.println(">>> BookertonService.updateMybook");

        Long bookertonId = mybookDto.getBookertonId();

        try {
//            MybookEntity mybookEntity = mybookRepository.findById(mybookDto.getBookertonId()).orElse(null);
            MybookEntity mybookEntity = mybookRepository.findBookIdByUserIdAndBookertonId(Long.parseLong(userId), bookertonId);
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
        List<MybookEntity> bookertonUserList = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();
        List<MybookDto> mybookDtoList = null;
        List<UserDto> userDtoList = null;

        try {
            mybookEntityList = mybookRepository.findByBookertonIdOrderByUserIdAsc(Long.parseLong(bookertonId));
            bookertonUserList = mybookRepository.findUserIdByBookertonId(Long.parseLong(bookertonId));

            for(MybookEntity user : bookertonUserList) {
                userIdList.add(user.getUserId());
            }
            System.out.println("userIdList : " + userIdList.toString());

            userEntityList = userRepository.findByUserIdIn(userIdList);

            System.out.println("userEntityList : " + userEntityList.toArray().toString());

            userDtoList = userEntityList.stream()
                    .map(userInfo -> {
                        UserDto userDto = new UserDto();
                        userDto.setUserId(userInfo.getUserId());
                        userDto.setUserNickname(userInfo.getUserNickname());
                        userDto.setUserProfileImgName(userInfo.getUserProfileImgName());
                        userDto.setUserProfileImgPath(userInfo.getUserProfileImgPath());

                        return userDto;
                    }).collect(Collectors.toList());

            mybookDtoList = mybookEntityList.stream()
                    .map(mybookInfo -> {
                        MybookDto mybookDto = new MybookDto();
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


        return ResponseDto.setSuccess("Success to get user list", result);
    }


//    /**
//     * Bookerton 마감 시 award + 1
//     * @param request
//     * @return
//     */
//    @Transactional
//    public ResponseDto<?> updateBookertonAward(Map<String, List<String>> request) {
//        System.out.println(">>> BookertonService.updateBookertonAward");
//
//        List<String> userIdList = request.get("userIdList");
//
//        // Id String -> Long 타입 변환
//        List<Long> userIdsAsLong = userIdList.stream()
//                .map(Long::parseLong)
//                .collect(Collectors.toList());
//
//        // userIdsAsLong에는 Long 타입의 값이 포함된 리스트가 저장됨
//        for (Long userId : userIdsAsLong) {
//            System.out.println(userId);
//        }
//
//        try {
//            // 조회 후 award + 1 update
//            List<UserEntity> userEntityList = userRepository.findByUserIdIn(userIdsAsLong);
//
//            for (UserEntity userEntity : userEntityList) {
//                userEntity.setUserAward(userEntity.getUserAward() + 1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseDto.setFailed("DB Error");
//        }
//
//
//        return ResponseDto.setSuccess("Success to update bookerton award!", null);
//    }

//    @Transactional
//    public ResponseDto<?> updateBookertonAward() {
//        System.out.println(">>> BookertonService.updateBookertonAward");
//        try {
//            String jpql = "SELECT tb FROM Bookerton tb " +
//                    "WHERE tb.bookertonEndDate = :endDate";
//
//            // 조회 후 award + 1 update
//            List<BookertonEntity> bookertonEntityList = findBookertonEntitiesByEndDate();
//            // bookerton_id List 작성
//            List<Long> bookertonIdList = bookertonEntityList.stream()
//                    .map(BookertonEntity::getBookertonId)
//                    .collect(Collectors.toList());
//
//            // 해당 북커톤에 참석한 모든 mybookEntityList 작성
//            List<MybookEntity> mybookEntityList = findMyBooksByBookertonIds(bookertonIdList);
//            // mybook_id List 작성
//            List<Long> mybookIdList = mybookEntityList.stream()
//                    .map(MybookEntity::getBookId)
//                    .collect(Collectors.toList());
//            // total_page = read_page 조건 부합하는 mybookId List 작성
//            List<MybookEntity> awardMybookList = findMyBooksByMatchingPagesInBookId(mybookIdList);
//
//            List<Long> awardUserList = awardMybookList.stream()
//                    .map(MybookEntity::getUserId)
//                    .collect(Collectors.toList());
//            // award 1 상승을 위한 userEntity 대상
//
//
//            List<UserEntity> userEntityList = userRepository.findByUserIdIn(awardUserList);
//
//            System.out.println("bookertonEntityList" + bookertonEntityList);
//            System.out.println("bookertonIdList" + bookertonIdList);
//            System.out.println("mybookIdList" + mybookIdList);
//            System.out.println("awardUserList" + awardUserList);
//            System.out.println("userEntityList" + userEntityList);
//
//
//            for (UserEntity userEntity : userEntityList) {
//                System.out.println("11111111111111111111111111111111111");
//                userEntity.setUserAward(userEntity.getUserAward() + 1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseDto.setFailed("DB Error");
//        }
//
//
//        return ResponseDto.setSuccess("Success to update bookerton award!", null);
//    }
//
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<BookertonEntity> findBookertonEntitiesByEndDate() {
//        String jpql = "SELECT tb FROM BookertonEntity tb " +
//                "WHERE tb.bookertonEndDate = :endDate";
//
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter("endDate", getFormattedYesterday());
//
//        // 형변환하여 결과를 BookertonEntity의 리스트로 반환
//        return (List<BookertonEntity>) query.getResultList();
//    }
//
//    public static String getFormattedYesterday() {
//        LocalDate yesterday = LocalDate.now().minusDays(1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        return yesterday.format(formatter);
//    }
//
//    public List<MybookEntity> findMyBooksByBookertonIds(List<Long> bookertonIds) {
//        String jpql = "SELECT mb FROM tb_mybook mb " +
//                "WHERE mb.bookerton.bookertonId IN :bookertonIds";
//
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter("bookertonIds", bookertonIds);
//
//        return query.getResultList();
//    }
//
//    public List<MybookEntity> findMyBooksByMatchingPagesInBookId(List<Long> bookIds) {
//        String jpql = "SELECT mb FROM tb_mybook mb " +
//                "WHERE mb.bookTotalPage = mb.bookReadPage" +
//                "AND mb.bookId IN :bookIds";
//
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter("bookIds", bookIds);
//
//        return query.getResultList();
//    }
}
