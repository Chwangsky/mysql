package com.woo.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.woo.boardback.dto.response.ResponseDto;
import com.woo.boardback.dto.response.search.GetPopularListResponseDto;
import com.woo.boardback.dto.response.search.GetRelationListResponseDto;
import com.woo.boardback.repository.SearchLogRepository;
import com.woo.boardback.repository.resultSet.GetPopularListResultSet;
import com.woo.boardback.repository.resultSet.GetRelationListResultSet;
import com.woo.boardback.service.SearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
        List<GetPopularListResultSet> popularList = new ArrayList<>();
        try {
            popularList =  searchLogRepository.getPopularList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetPopularListResponseDto.success(popularList);
        
    }

    @Override
    public ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord) {
        List<GetRelationListResultSet> resultSets = new ArrayList<>();
        try {
            resultSets = searchLogRepository.getRelationList(searchWord);

        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRelationListResponseDto.success(resultSets);
    }
    
}
