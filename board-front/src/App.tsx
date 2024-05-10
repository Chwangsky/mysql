import './App.css';
import { useEffect, useRef, useState } from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import Main from 'views/Main';
import Authentication from 'views/Authentication';
import UserPage from 'views/User';
import Search from 'views/Search';
import BoardDetail from 'views/Board/Detail';
import BoardWrite from 'views/Board/Write';
import BoardUpdate from 'views/Board/Update';
import Container from 'layouts/Container';
import { MAIN_PATH, AUTH_PATH, SEARCH_PATH, USER_PATH, BOARD_PATH, BOARD_WRITE_PATH, BOARD_DETAIL_PATH, BOARD_UPDATE_PATH, OAUTH_PATH } from 'constant';
import { useCookies } from 'react-cookie';
import { useLoginUserStore } from 'stores';
import { GetSignInUserResponseDto } from 'apis/response/user';
import { User } from 'types/interface';
import { getSignInUserRequest } from 'apis';
import { ResponseDto } from 'apis/response';
import OAuth from 'views/Authentication/OAuth';

//          component: Application 컴포넌트     //
function App() {
  
  //          state: 로그인 유저 전역 상태          //
  const { setLoginUser, resetLoginUser } = useLoginUserStore(); 

  //          state: cookie 상태          //
  const [cookies, setCookie] = useCookies();

  //          state: useEffect 2번 실행 방지하기 위한 변수           //
  const stateForRunOnlyOnce = useRef(false);
  
  //          effect: accessToken cookie 값이 변경될 때마다 실행될 함수          //
  useEffect(() => {
    if (stateForRunOnlyOnce.current) return;
    if (!cookies.accessToken) {
      resetLoginUser();
      return;
    }
    getSignInUserRequest(cookies.accessToken).then(getSignInUserResponse);
    stateForRunOnlyOnce.current = true;
  }, [cookies.accessToken])

  //          function: get sign in user response 처리 함수          //

  const getSignInUserResponse = (responseBody: GetSignInUserResponseDto | ResponseDto | null) => {
    if (!responseBody) return;
    const { code } = responseBody;
    if (code === "AF" || code === 'NU' || code === "DBE" ) {
      resetLoginUser();
      return;
    }
    
    const loginUser: User = { ...(responseBody as GetSignInUserResponseDto) };
    setLoginUser(loginUser);
  }

  //          render: Application 컴포넌트 렌더링    //
  // description: 리다이렉트 : '/' 으로 접속 시 home으로 이동
  // description: 메인 화면 : '/' - Main //
  // description: 로그인 + 회원가입 화면 : '/auth' - Authentication //
  // description: 검색 화면 : '/search/:word' - Search //
  // description: 유저 페이지 : '/user/:userEmail' - Search //
  // description: 게시물 상세보기 : '/board/detail/:boardNumber' - BoardDetail //
  // description: 게시물 작성하기: '/board/write' - BoardWrite //
  // description: 게시물 수정하기: '/board/update/:boardNumber' - BoardUpdate //
  return (
    <Routes>
      <Route element={<Container />}>
        <Route path={OAUTH_PATH(':token', ':expirationTime')} element={<OAuth />} />
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path={AUTH_PATH()} element={<Authentication />} />
        <Route path={SEARCH_PATH(':searchWord')} element={<Search />} />
        <Route path={USER_PATH(':encodedUserEmail')} element={<UserPage />} />
        <Route path={BOARD_PATH()}>
          <Route path={BOARD_WRITE_PATH()} element={<BoardWrite />} />
          <Route path={BOARD_DETAIL_PATH(':boardNumber')} element={<BoardDetail />} />
          <Route path={BOARD_UPDATE_PATH(':boardNumber')} element={<BoardUpdate />} />
        </Route>
        <Route path='*' element={<h1>404 Not Found</h1>} />
      </Route>
    </Routes>
  );
}

export default App;
