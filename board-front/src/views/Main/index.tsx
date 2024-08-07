import React, { useEffect, useState } from 'react'
import './style.css';
import Top3Item from 'components/Top3Item';
import { BoardListItem } from 'types/interface';
import { latestBoardListMock, top3BoardListMock } from 'mocks';
import BoardItem from 'components/BoardItem';
import Pagination from 'components/Pagination';
import { useNavigate } from 'react-router-dom';
import { SEARCH_PATH } from 'constant';
import { getLatestBoardListRequest, getPopularListRequest, getTop3BoardListRequest } from 'apis';
import { GetLatestBoardListResponseDto, GetTop3BoardListResponseDto } from 'apis/response/board';
import { ResponseDto } from 'apis/response';
import { usePagination } from 'hooks';
import { GetPopularListResponseDto } from 'apis/response/search';


export default function Main() {

  const MainTop = () => {

    const [top3BoardList, setTop3BoardList] = useState<BoardListItem[]>([]);

    useEffect(() => {
      // setTop3ListBoard(top3BoardListMock);
      getTop3BoardListRequest().then(getTop3BoardListResponse);
    }, []);

    const getTop3BoardListResponse = (responseBody: GetTop3BoardListResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'DBE') alert("데이터베이스 오류입니다.");
      if (code !== 'SU') return;

      const { top3List } = responseBody as GetTop3BoardListResponseDto;
      if (!top3List) return;
      setTop3BoardList(top3List);
    }

    //          render: 메인화면 상단 컴포넌트 렌더링          //
    return (
      <div id='main-top-wrapper'>
        <div className='main-top-container'>
          <div className='main-top-title'>{'DEV. 0woo의 프론트/백엔드 테스팅 공간입니다.'}</div>
          <div className='main-top-title'>{'수익창출 목적은 없으며, 오로지 기능 테스팅만을 위해 사용합니다.'}</div>
          <div className='main-top-contents-box'>
            <div className='main-top-contents-title'>{'가장 인기 많은 게시글'}</div>
            <div className='main-top-contents'>
              { top3BoardList.length > 0 ? top3BoardList.map(item => <Top3Item top3ListItem={item}/>) : <div>{'최근 등록된 게시물이 없습니다.'}</div>}
            </div>
          </div>
        </div>
      </div>
    )
  }

  //          component: 메인화면 하단 컴포넌트          //
  const MainBottom = () => {

    //          function: 네비게이트 함수          //
    const navigate = useNavigate();


    //          state: 페이지 네이션 관련 상태          //
    const { 
      currentPage,
      setCurrentPage,
      currentSection,
      setCurrentSection,
      viewList,
      viewPageList,
      totalSection,
      setTotalList } = usePagination<BoardListItem>(5);

    //          state: 인기 검색어 리스트 상태          //
    const [popularWordList, setPopularWordList] = useState<string[]>([]);

    //          event handler : 인기 검색어 클릭 이벤트 처리          //
    const onPopularWordClickHandler = (word: string) => {
      navigate(SEARCH_PATH(word));
    }

    //          effect: 첫 마운트 시 실행될 함수          //
    useEffect(() => {
      getLatestBoardListRequest().then(getLatestBoardListResponse);
      getPopularListRequest().then(getPopularListResponse);
    }, [])

    //          function: getLatestBoardListResponse 처리 함수          //
    const getLatestBoardListResponse = (responseBody: GetLatestBoardListResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'DBE') alert('데이터베이스 에러입니다.');
      if (code !== 'SU') return;

      const { latestList } = responseBody as GetLatestBoardListResponseDto;
      setTotalList(latestList);
    }

    //          function: getPopularListResponse 처리 함수          //
    const getPopularListResponse = (responseBody: GetPopularListResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'DBE') alert('데이터베이스 에러입니다.');
      if (code !== 'SU') return;

      const { popularWordList } = responseBody as GetPopularListResponseDto;
      setPopularWordList(popularWordList);
    }
    

    //          render: 메인화면 하단 컴포넌트 렌더링          //
    return (

      <div id='main-bottom-wrapper'>
        <div className='main-bottom-container'>
          <div className='main-bottom-title'>{'최신 게시물'}</div>
          <div className='main-bottom-contents-box'>
            <div className='main-bottom-current-contents'>
              {viewList.map((boardListItem) => <BoardItem boardListItem = {boardListItem}/>)}
            </div>
            <div className='main-bottom-popular-box'>
              <div className='main-bottom-popular-card'>
                <div className='main-bottom-popular-card-container'>
                  <div className='main-bottom-popular-card-title'>{'인기 검색어'}</div>
                  <div className='main-bottom-popular-card-contents'>
                    {popularWordList.map(word => <div className='word-badge' onClick={() => onPopularWordClickHandler(word)}>{word}</div>)}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className='main-bottom-pagination-box'>
            <Pagination
              currentPage={currentPage}
              currentSection={currentSection}
              setCurrentPage={setCurrentPage}
              setCurrentSection={setCurrentSection}
              viewPageList={viewPageList}
              totalSection={totalSection}/>
          </div>
        </div>
      </div>
    )
  }

  return (

    //          render: 메인화면 컴포넌트 렌더링          //
    <div>
      <MainTop/>
      <MainBottom/>
    </div>
  )
}
