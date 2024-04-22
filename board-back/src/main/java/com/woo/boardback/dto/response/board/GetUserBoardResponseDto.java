package com.woo.boardback.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.woo.boardback.common.ResponseCode;
import com.woo.boardback.common.ResponseMessage;
import com.woo.boardback.dto.object.BoardListItem;
import com.woo.boardback.dto.response.ResponseDto;
import com.woo.boardback.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetUserBoardResponseDto extends ResponseDto {
    private List<BoardListItem> userBoardList;

    private GetUserBoardResponseDto(List<BoardListViewEntity> boardListViewEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userBoardList = BoardListItem.getList(boardListViewEntities);
    }

    public static ResponseEntity<? super GetUserBoardResponseDto> success(List<BoardListViewEntity> boardListViewEntities) {
        GetUserBoardResponseDto result = new GetUserBoardResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }


    
}
