package com.woo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.woo.boardback.dto.request.board.PatchBoardRequestDto;
import com.woo.boardback.dto.request.board.PostBoardRequestDto;
import com.woo.boardback.dto.request.board.PostCommentRequestDto;
import com.woo.boardback.dto.response.board.DeleteBoardResponseDto;
import com.woo.boardback.dto.response.board.GetBoardResponseDto;
import com.woo.boardback.dto.response.board.GetCommentListResponseDto;
import com.woo.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.woo.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.woo.boardback.dto.response.board.GetSearchBoardListResponseDto;
import com.woo.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.woo.boardback.dto.response.board.GetUserBoardResponseDto;
import com.woo.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.woo.boardback.dto.response.board.PatchBoardResponseDto;
import com.woo.boardback.dto.response.board.PostBoardResponseDto;
import com.woo.boardback.dto.response.board.PostCommentResponseDto;
import com.woo.boardback.dto.response.board.PutFavoriteResponseDto;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);
    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();
    ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();

    ResponseEntity<? super GetUserBoardResponseDto> getUserBoardList(String email);

    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);

    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);
    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);

    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);

    ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord);
}