package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.PartyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<PartyEntity, Long> {
    // 파티 생성 - 파티명 중복 여부 확인
    boolean existsByPartyName(String partyName);

    // 파티 생성
    PartyEntity findByPartyName(String partyName);

    // Main - party list 조회 (최신 순 + n개 ~ m개 조회)
    //List<PartyEntity> findByPartyCategoryInOrderByPartyCreationDateDesc(List<String> categories, Pageable pageable);
    List<PartyEntity> findByPartyCategoryInOrderByPartyCreationDateDesc(List<String> categories);
    List<PartyEntity> findByPartyNameLikeOrderByPartyCreationDateDesc(String partyName);
    List<PartyEntity> findAllByOrderByPartyCreationDateDesc(Pageable pageable);

    List<PartyEntity> findByPartyNameLikeAndPartyCategoryInOrderByPartyCreationDateDesc(String partyName, List<String> categories);

    // partyInfo
    PartyEntity findByPartyId(Long partyId);

    List<PartyEntity> findByPartyIdIn(List<Long> partyIdList);

}
