package com.woo.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woo.boardback.entity.BoardEntity;
import com.woo.boardback.repository.resultSet.GetBoardResultSet;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    boolean existsByBoardNumber(Integer boardNumber);

    @Query(
        value=
        "SELECT " +
        "B.BOARD_NUMBER AS boardNumber, " + 
        "B.TITLE AS title, " +
        "B.CONTENT AS content, " +
        "B.WRITE_DATETIME AS writeDateTime, " +
        "B.WRITER_EMAIL AS writerEmail, " +
        "U.NICKNAME AS writerNickname, " +
        "U.PROFILE_IMAGE AS writerProfileImage " +
        "FROM board B " +
        "JOIN USER U " +
        "ON B.WRITER_EMAIL = U.EMAIL " +
        "WHERE B.BOARD_NUMBER = ?1 ",
        nativeQuery=true
    )
    GetBoardResultSet getBoard(Integer boardNumber);

    BoardEntity findByBoardNumber(Integer boardNumber);
}
