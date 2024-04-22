package com.woo.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woo.boardback.entity.FavoriteEntity;
import com.woo.boardback.entity.primaryKey.FavoritePk;
import com.woo.boardback.repository.resultSet.GetFavoriteListResultSet;

import jakarta.transaction.Transactional;

/**
 * jpa로 복합키를 표시하는 방법
 * 1-1. entity 폴더 하위에 primaryKey폴더를 두고, FavoritePk.java를 둠.
 * 1-2. FavoritePk.java는 Serializable를 구현
 * 2. FavoriteEntity에 @idClass 추가
 * 3. FavoriteRepository에서는 FavoritePk를 사용
 */

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk> {

    FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

    @Query(
        value=
        "SELECT " +
        "    U.EMAIL AS email, " +
        "    U.nickname AS nickname, " +
        "    U.PROFILE_IMAGE as profileImage " +
        "FROM FAVORITE as F " +
        "INNER JOIN user as U " +
        "ON F.USER_EMAIL = U.EMAIL " +
        "WHERE F.BOARD_NUMBER = ?1",
        nativeQuery = true
    )
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);

}