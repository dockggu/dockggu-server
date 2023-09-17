package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.PartyResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    PartyRepository partyRepository;

    public ResponseDto<List<PartyResponseDto>> getPartyListCategory(int page) {
        System.out.println("MainService.getPartyListCategory");
        List<PartyResponseDto> result = partyRepository.findByOrderByObjectCreateDateDesc(PageRequest.of(page, 30));

        return ResponseDto.setSuccess("Success to get list!", result);
    }
}
