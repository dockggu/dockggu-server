package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.ParticipantEntity;
import com.DXsprint.dockggu.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {

}
