package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.BookertonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookertonRepository extends JpaRepository<BookertonEntity, Long> {
    List<BookertonEntity> findAllByPartyIdAndBookertonStatus(Long partyId, String bookertonState);
}
