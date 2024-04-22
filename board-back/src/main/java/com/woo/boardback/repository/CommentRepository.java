package com.woo.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woo.boardback.entity.CommentEntity;
import com.woo.boardback.repository.resultSet.GetCommentListResultSet;

import jakarta.transaction.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query(
        value=
        "SELECT " +
        "   U.NICKNAME AS nickname, " +
        "   U.profile_image AS profileImage, " +
        "   C.WRITE_DATETIME AS writeDatetime, " +
        "   C.CONTENT AS content    " +
        "   FROM COMMENT AS C " +
        "   INNER JOIN USER AS U " +
        "   ON C.USER_EMAIL = U.email " +
        "   WHERE C.BOARD_NUMBER = ?1 " +
        "   ORDER BY writeDatetime DESC ",
        nativeQuery = true
    )
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);


}
