package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.CategoryDto;
import com.DXsprint.dockggu.dto.PartyResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    @Autowired
    PartyRepository partyRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<PartyResponseDto>> getPartyListCategory(List<String> categories, int pageNum) {
        System.out.println("MainService.getPartyListCategory");
        List<PartyResponseDto> result = null;
        PageRequest pageable = PageRequest.of(pageNum, 30);

        try {
            System.out.println("카테고리 : " + categories.get(0));
            System.out.println("카테고리 : " + categories.get(1));
            System.out.println("카테고리 : " + categories.get(2));

            if(categories.get(0).equals("bc0000"))
                result = partyRepository.findByPartyCategoryInOrderByPartyCreationDateDesc(categories, pageable);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get list!", result);
    }
}
