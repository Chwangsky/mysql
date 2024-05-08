import React, { ChangeEvent, KeyboardEvent, useEffect, useRef, useState } from 'react'
import './style.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AUTH_PATH, BOARD_DETAIL_PATH, BOARD_PATH, BOARD_UPDATE_PATH, BOARD_WRITE_PATH, MAIN_PATH, SEARCH_PATH, USER_PATH } from 'constant';
import { useCookies } from 'react-cookie';
import { useBoardStore, useLoginUserStore } from 'stores';
import { fileUploadRequest, logoutRequest, patchBoardRequest, postBoardRequest } from 'apis';
import { PatchBoardRequestDto, PostBoardRequestDto } from 'apis/request/board';
import { PatchBoardResponseDto, PostBoardResponseDto } from 'apis/response/board';
import { ResponseDto } from 'apis/response';

//          component: 헤더 레이아웃          //
export default function Header() {

  //          state: 로그인 유저 상태          //
  const { loginUser, resetLoginUser } = useLoginUserStore()

  //          state: path 상태          //
  const { pathname } = useLocation();

  //          state: cookie 상태          //
  const [cookies, setCookie] = useCookies();

  //          state: 로그인 상태          //
  const [isLogin, setLogin] = useState<boolean>(false);

  //          state: 인증 페이지 상태          //
  const [isAuthPage, setAuthPage] = useState<boolean>(false);
  //          state: 메인 페이지 상태          //
  const [isMainPage, setMainPage] = useState<boolean>(false);
  //          state: 검색 페이지 상태 상태          //
  const [isSearchPage, setSearchPage] = useState<boolean>(false);
  //          state: 게시물 상세 페이지 상태 상태          //
  const [isBoardDetailPage, setBoardDetailPage] = useState<boolean>(false);
  //          state: 게시물 작성 페이지 상태 상태          //
  const [isBoardWritePage, setBoardWritePage] = useState<boolean>(false);
  //          state: 게시물 수정 페이지 상태 상태          //
  const [isBoardUpdatePage, setBoardUpdatePage] = useState<boolean>(false);
  //          state: 유저 페이지 상태 상태          //
  const [isUserPage, setUserPage] = useState<boolean>(false);

  //          effect: path가 변경될 때마다 실행될 함수          //
  useEffect(() => {
    const isAuthPage = pathname.startsWith(AUTH_PATH());
    setAuthPage(isAuthPage);
    const isMainPage = pathname.startsWith(MAIN_PATH());
    setMainPage(isMainPage);
    const isSearchPage = pathname.startsWith('/' + SEARCH_PATH(''));
    setSearchPage(isSearchPage);
    const isBoardDetailPage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_DETAIL_PATH(''));
    setBoardDetailPage(isBoardDetailPage);
    const isBoardWritePage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_WRITE_PATH());
    setBoardWritePage(isBoardWritePage)
    const isBoardUpdatePage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_UPDATE_PATH(''));
    setBoardUpdatePage(isBoardUpdatePage)
    const isUserPage = pathname.startsWith(USER_PATH(''));
    setUserPage(isUserPage);
  }, [pathname])

  //          function: 네비게이트 함수          //
  const navigate = useNavigate();

  //          event handler: 로고 클릭 이벤트 처리 함수          //
  const onLogoClickHandler = () => {
    navigate(MAIN_PATH());
  }

  //          component: 검색 버튼 컴포넌트           //
  const SearchButton = () => {

    //          state: 검색어 버튼 요소 참조 상태          //
    const searchButtonRef = useRef<HTMLDivElement | null>(null);
    //          state: 검색 버튼 상태          //
    const [status, setStatus] = useState<boolean>(false);
    //          state: 검색어 상태          //
    const [word, setWord] = useState<string>('');
    //          state: 검색어 path variable 상태          //
    const { searchWord } = useParams() ; // App.tsx의 searchWord와 동일한 이름으로 작성해야 함.

    //          event handler: 검색어 변경 이벤트 처리 함수          //
    const onSearchWordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const value = event.target.value; // 이벤트 객체에서 입력 필드의 값을 가져옴
      setWord(value); // setWord  함수를 사용하여 검색어 상태 변경
    };

    //          event handler: 검색어 키 이벤트 처리 함수          //
    const onSearchWordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!searchButtonRef.current) return;
      searchButtonRef.current.click();
    };

    //          event handler: 검색 버튼 클릭 이벤트 처리 함수          //
    const onSearchButtonClickHandler = () => {
      if (!status) {
        setStatus(!status);
        return;
      }
      navigate(SEARCH_PATH(word));
      setStatus(false);
    };

    //          effect: 검색어 path variable이 변경될 때마다 실행될 함수           //
    useEffect(() => {
      if (searchWord) {
        setWord(searchWord);
        setStatus(false);
      }
    }, [searchWord]);

    if (!status)
      //          render: 클릭 false 상태인 경우 검색 버튼 컴포넌트 렌더링          //
      return (
        <div className='icon-button' onClick={onSearchButtonClickHandler}>
          <div className='icon search-light-icon'></div>
        </div>
      )

    //          render: 클릭 true 상태인 경우 검색 버튼 컴포넌트 렌더링          //
    return (
      <div className='header-search-input-box'>
        <input className='header-search-input' type='text' placeholder='검색어를 입력해 주세요.' value={word} onChange={onSearchWordChangeHandler} onKeyDown={onSearchWordKeyDownHandler}/>
        <div ref={searchButtonRef} className='icon-button' onClick={onSearchButtonClickHandler}>
          <div className='icon search-light-icon'></div>
        </div>
      </div>
    )
  };

  //          component: 로그인 또는 마이페이지 버튼 컴포넌트          //
  const MyPageButton = () => {

    //          state: encodedUserEmail Path variable 상태           //
    const { encodedUserEmail } = useParams();

    const userEmail = encodedUserEmail? decodeURIComponent(encodedUserEmail): 'unlogined@email.com';

    //          event handler : 마이페이지 버튼 클릭 이벤트 처리 함수          //
    const onMyPageButtonClickHandler = () => {
      if (!loginUser) return;
      const { email } = loginUser;
      
      // navigate(USER_PATH(email));
      const encodedEmail = encodeURIComponent(email);
      navigate(USER_PATH(encodedEmail));
    };  

    //          event handler : 로그아웃 버튼 클릭 이벤트 처리 함수           //
    const onSignOutButtonClickHandler = async () => {
      const response = await logoutRequest();
      console.log(response);

      if (response && response.code === "SU") {
          // Reset the user state
          resetLoginUser();

          // Clear the access token cookie
          setCookie("accessToken", "", { path: MAIN_PATH(), expires: new Date() });

          // Navigate to the main page
          navigate(MAIN_PATH());
          alert("로그아웃되었습니다.")
      } else {
          alert("로그아웃이 실패했습니다.");
          // Handle the error, possibly with a user notification
      }
    };

    //          event handler : 로그인 버튼 클릭 이벤트 처리 함수          //
    const onSignInButtonClickHandler = () => {
      navigate(AUTH_PATH())
    };
    

    if (isLogin && userEmail === loginUser?.email) {

      return (
      <>
      <div className='white-button' onClick={onMyPageButtonClickHandler}>{'마이페이지'}</div>
      <div className='white-button' onClick={onSignOutButtonClickHandler}>{'로그아웃'}</div>
      </>
      );
    }

    //          render: 마이페이지 버튼 컴포넌트 렌더링          //
    if (isLogin) {
      return <div className='white-button' onClick={onMyPageButtonClickHandler}>{'마이페이지'}</div>;
    }

    //          render: 로그인 버튼 컴포넌트          //
    return <div className='black-button' onClick={onSignInButtonClickHandler}>{'로그인'}</div>;
  }

  //          component: 업로드 버튼 컴포넌트          //
  const UploadButton = () => {

    //          state: 게시물 번호 path variable 상태         //
    const { boardNumber } = useParams();

    //          state: 게시물 상태          //
    const { title, content, boardImageFileList, resetBoard } = useBoardStore();

    //          event handler: 업로드 버튼 클릭 이벤트 처리 함수          //
    const onUploadButtonClickHandler = async () => {
      
      const accessToken = cookies.accessToken;
      if (!accessToken) return;
      const boardImageList: string[] = [];
      
      for (const file of boardImageFileList) {
        const data = new FormData();
        data.append('file', file);
        
        const url = await fileUploadRequest(data);
        if (url) boardImageList.push(url);
      }

      const isWriterPage = pathname === BOARD_PATH() + '/' + BOARD_WRITE_PATH();
      if (isWriterPage) {
        const requestBody: PostBoardRequestDto = {
          title, content, boardImageList
        }
        postBoardRequest(requestBody, accessToken).then(postBoardResponse);
      } else {
        if (!boardNumber) return;
        const requestBody: PatchBoardRequestDto = {
          title, content, boardImageList
        }
        patchBoardRequest(boardNumber, requestBody, accessToken).then(patchBoardResponse);
      }
    }
    
    //          function: post board response 처리 함수          //
    const postBoardResponse = (responseBody: PostBoardResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody
      if (code === 'AF' || code === 'NU') { navigate(AUTH_PATH()) }
      if (code === 'DBE') alert("데이터베이스 오류입니다.");
      if (code === 'VF') alert("제목과 내용은 필수입니다.");
      if (code !== 'SU') return;

      resetBoard();
      if (!loginUser) return;
      const { email } = loginUser;
      // navigate(USER_PATH(email));
      const encodedEmail = encodeURIComponent(email);
      navigate(USER_PATH(encodedEmail));
    }

    //          function: patch board response 처리 함수          //
    const patchBoardResponse = (responseBody: PatchBoardResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody
      if (code === 'DBE') alert("데이터베이스 오류입니다.");
      if (code === 'AF' || code === 'NU' || code === 'NB' || code === 'NP') { navigate(AUTH_PATH()) }
      if (code === 'VF') alert("제목과 내용은 필수입니다.");
      if (code !== 'SU') return;

      if (!boardNumber) return;
      if (!loginUser) return;
      navigate(BOARD_PATH() + '/' + BOARD_DETAIL_PATH(boardNumber));
    }
  
    //          render: 업로드 버튼 렌더링          //
    if (title && content) 

    return <div className='black-button' onClick={onUploadButtonClickHandler}>{'업로드'}</div>;

    //          render: 업로드 불가 버튼 렌더링          //
    return <div className='disable-button'>{'업로드'}</div>;
  }

  

  //          effect: login User가 변경된 경우에 실행될 함수         //
  useEffect(() => {
    setLogin(loginUser !== null);
  }, [loginUser])

  //          effect:           //

  //          render: 헤더 레이아웃 렌더링          //
  return (
    <div id='header'>
      <div className='header-container'>
        <div className='header-left-box' onClick={onLogoClickHandler}>
          <div className='icon-box'>
            <div className='icon logo-dark-icon'></div>
          </div>
          <div className='header-logo'>{'0woo Board'}</div>
        </div>
        <div className='header-right-box'>  
          {(isAuthPage || isMainPage || isSearchPage || isBoardDetailPage) && <SearchButton />}
          {(isMainPage || isSearchPage || isBoardDetailPage || isUserPage) && <MyPageButton />}
          {(isBoardWritePage || isBoardUpdatePage) && <UploadButton />}
        </div>
      </div>
    </div>
  )
}
