package com.DXsprint.dockggu.service;

import com.DXsprint.dockggu.dto.CategoryDto;
import com.DXsprint.dockggu.dto.PartyResponseDto;
import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.entity.PartyEntity;
import com.DXsprint.dockggu.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseDto<List<PartyEntity>> getPartyListCategory(CategoryDto categoryDto) {
        System.out.println("MainService.getPartyListCategory");

        List<PartyEntity> result = null;
        int pageNum = Integer.parseInt(categoryDto.getPage());
        System.out.println("start Page : " + pageNum);

        PageRequest pageable = PageRequest.of(pageNum, 30);
        List<String> categories = categoryDto.getCategories();
        try {
            for(int i=0; i<5; i++) {
                System.out.println("카테고리 : " + categories.get(i));
            }
//            result = partyRepository.findByPartyCategoryInOrderByPartyCreationDateDesc(categories, pageable);
            result = partyRepository.findByPartyCategoryInOrderByPartyCreationDateDesc(categories);

            if(categories.get(0).equals("bc0000"))
                result = partyRepository.findAllByOrderByPartyCreationDateDesc(pageable);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get list!", result);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<PartyEntity>> getPartyListSearch(String page, String partyName) {
        System.out.println("MainService.getPartyListSearch");

        List<PartyEntity> result = null;

        try {
            result = partyRepository.findByPartyNameContainingOrderByPartyCreationDateDesc(partyName);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed("DB error");
        }

        return ResponseDto.setSuccess("Success to get list!", result);
    }
}
