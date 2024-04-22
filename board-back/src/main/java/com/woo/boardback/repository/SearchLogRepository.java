package com.woo.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woo.boardback.entity.SearchLogEntity;
import com.woo.boardback.repository.resultSet.GetPopularListResultSet;
import com.woo.boardback.repository.resultSet.GetRelationListResultSet;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLogEntity, Integer> {
    
    @Query(
        value =
        "SELECT SEARCH_WORD as searchWord, COUNT(SEARCH_WORD) AS COUNT " +
        "FROM search_log " +
        "WHERE RELATION IS FALSE " +
        "GROUP BY SEARCH_WORD " +
        "ORDER BY COUNT DESC " +
        "LIMIT 15 ",
        nativeQuery = true
    )
    List<GetPopularListResultSet> getPopularList();

    @Query(
        value = 
        "SELECT RELATION_WORD as searchWord, COUNT(RELATION_WORD) AS COUNT " +
        "FROM search_log " +
        "WHERE SEARCH_WORD = ?1 " +
        "AND relation_word IS NOT NULL " +
        "GROUP BY RELATION_WORD  " +
        "ORDER BY COUNT DESC " +
        "LIMIT 15 ",
        nativeQuery = true
    )
    List<GetRelationListResultSet> getRelationList(String searchWord);
}
