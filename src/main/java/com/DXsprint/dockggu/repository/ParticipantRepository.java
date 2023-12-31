package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.ParticipantEntity;
import com.DXsprint.dockggu.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {

    List<ParticipantEntity> findUserIdsByPartyId(Long partyId);

    List<ParticipantEntity> findPartyIdsByUserId(Long userId);


    @Modifying
    @Query("DELETE FROM participant p WHERE p.userId = :userId")
    void deleteByUserId(Long userId);
}
