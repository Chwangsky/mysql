import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import './style.css';
import { BoardListItem, User } from 'types/interface';
import { useLoginUserStore } from 'stores';
import defaultProfileImage from 'assets/image/default-profile-image.png';
import { useNavigate, useParams } from 'react-router-dom';
import { latestBoardListMock } from 'mocks';
import BoardItem from 'components/BoardItem';
import { BOARD_PATH, BOARD_WRITE_PATH, MAIN_PATH, USER_PATH } from 'constant';
import { fileUploadRequest, getUserBoardListRequest, getUserRequest, patchNicknameRequest, patchProfileImageRequest } from 'apis';
import { GetUserResponseDto, PatchNicknameResponseDto, PatchProfileImageResponseDto } from 'apis/response/user';
import { ResponseDto } from 'apis/response';
import { PatchNicknameRequestDto, PatchProfileImageRequestDto } from 'apis/request/user';
import { useCookies } from 'react-cookie';
import { usePagination } from 'hooks';
import { GetUserBoardListResponseDto } from 'apis/response/board';
import Pagination from 'components/Pagination';

//          component: 유저 화면 컴포넌트          //
export default function UserPage() {

  //          state: 유저 이메일 path variable 상태          //
  const { userEmail } = useParams();
  //          state: cookie 상태         //
  const [cookies, setCookies] = useCookies();
  //          state: 로그인 유저 상태          //
  const { loginUser, setLoginUser } = useLoginUserStore();
  //          state: 마이페이지 여부 상태          //
  const [isMyPage, setMyPage] = useState<boolean>(true);

  //          function: navigate          //
  const navigate = useNavigate();


  //          component: 유저 상단화면 컴포넌트          //
  const UserTop = () => {

    //          state: 이미지 파일 인풋 참조 상태          //
    const imageInputRef = useRef<HTMLInputElement | null>(null);
    //          state: 닉네임 변경 여부 상태 (닉네임 수정 버튼 클릭 시 on, off)          //
    const [isNicknameChange, setIsNicknameChange] = useState<boolean>(false);
    //          state: 닉네임 상태          //
    const [nickname, setNickname] = useState<string>('');
    //          state: 변경 닉네임 상태          //
    const [changeNickname, setChangeNickname] = useState<string>(nickname);
    //          state: 프로필 이미지 상태        //
    const [profileImage, setProfileImage] = useState<string | null>(null);

    //          event handler: 프로필 박스 클릭 이벤트 처리          //
    const onProfileBoxClickHandler = () => {
      
      if(!isMyPage) return; // mypage가 아닌 경우에는 프로필박스 자체가 보이지 않으므로 불필요
      if(!imageInputRef.current) return;
      imageInputRef.current.click();
    }

    //          event handler: 닉네임 수정 버튼 클릭 이벤트 처리          //
    const onNicknameEditButtonClickHandler = () => {

      if (!isNicknameChange) {
        setChangeNickname(nickname);
        setIsNicknameChange(true);
        return;
      }

      if (!cookies.accessToken) return;
      const requestBody: PatchNicknameRequestDto = {
        nickname: changeNickname
      };
      patchNicknameRequest(requestBody, cookies.accessToken).then(patchNicknameResponse);
      setIsNicknameChange(false);
    }

    //          function: patchNicknameResponse 처리 함수          //
    const patchNicknameResponse = (responseBody: PatchNicknameResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'VF') alert('닉네임은 필수입니다.')
      if (code === 'AF') alert('인증에 실패했습니다.')
      if (code === 'DN') alert('중복된 닉네임입니다.')
      if (code === 'NU') alert("존재하지 않는 유저입니다.");
      if (code === 'DBE') alert("데이터베이스 오류입니다.");
      if (code !== "SU") return;

      if (!userEmail) return;
      console.log("중단점1"); // DOESN't WORK
      getUserRequest(userEmail).then(getUserResponse);
      
    }

    //          effect: user email variable 변경시 실행 함수          //
    useEffect(() => {
      if (!userEmail) return;
      getUserRequest(userEmail).then(getUserResponse);
    }, [userEmail])

    //          function: getUserResponse 처리 함수          //
    const getUserResponse = (responseBody: GetUserResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'NU') alert("존재하지 않는 유저입니다.");
      if (code === 'DBE') alert("데이터베이스 오류입니다.");
      if (code !== "SU") {
        navigate(MAIN_PATH())
      }

      const { email, nickname, profileImage } = responseBody as GetUserResponseDto;
      // setLoginUser(email, nickname, profileImage);
      setNickname(nickname);
      setProfileImage(profileImage);
      const isMyPage = email === loginUser?.email;
      setMyPage(isMyPage);
    }

    //          event handler: 프로필 이미지 변경 이벤트 처리          //
    const onProfileImageChangehandler = (event: ChangeEvent<HTMLInputElement>) => {
      if (!event.target.files || !event.target.files.length) return;
      
      const file = event.target.files[0];
      const data = new FormData();
      data.append('file', file);

      // TODO // 
      fileUploadRequest(data).then(fileUploadResponse)
    }

    //          function: fileUploadResponse 처리 함수          //
    const fileUploadResponse = (profileImage: string | null ) => {
      if (!profileImage) return;
      if(!cookies.accessToken) return;

      const requestBody: PatchProfileImageRequestDto = { profileImage };
      patchProfileImageRequest(requestBody, cookies.accessToken).then(patchProfileImageResponse);
    }

    //          function: patchProfileImageResponse 처리 함수          //
    const patchProfileImageResponse = (responseBody: PatchProfileImageResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody
      if (code === 'VF') alert('잘못된 접근입니다.');
      if (code === 'AF') alert('존재하지 않는 유저입니다.');
      if (code === 'DBE') alert('데이터베이스 오류입니다.');
      if (code !== 'SU') return;

      if (!userEmail) return;
      getUserRequest(userEmail).then(getUserResponse);
    }


    //          event handler: 닉네임 변경 이벤트 처리          //
    const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const { value } = event.target;
      setChangeNickname(value);
    }

    //          render: 유저 상단화면 컴포넌트 렌더링          //
    return (
      <div id='user-top-wrapper'>
        <div className='user-top-container'>
          {isMyPage ? (
            <div className='user-top-my-profile-image-box' onClick={onProfileBoxClickHandler}>
              {profileImage !== null ? (
                <div className='user-top-profile-image' style={{ backgroundImage:`url(${profileImage})`}}></div>
              ) : (
                <div className='user-top-my-profile-nothing-box'>
                  <div className='icon-box-large'>
                    <div className='icon image-box-white-icon'></div>
                  </div>
                </div>
              )}
              
              <input ref={imageInputRef} type="file" accept='image/*' style={{display: 'none'}} onChange={onProfileImageChangehandler}/>
            </div>
          ) : (
            <div className='user-top-profile-image-box' style={{ backgroundImage:`url(${defaultProfileImage})`}}></div>
          )}
          
          <div className='user-top-info-box'>
            <div className='user-top-info-nickname-box'>
              { isMyPage ? (
              <>
              { isNicknameChange ? (
                <input className='user-top-info-nickname-input' type='text' size={nickname.length + 2} value={changeNickname} onChange={onNicknameChangeHandler} />
              ) : (
              <div className='user-top-info-nickname'>{nickname}</div>
              )}
              
              <div className='icon-button' onClick={onNicknameEditButtonClickHandler}>
                <div className='icon edit-icon'></div>
              </div>
              </>
              ) : (
                <div className='user-top-info-nickname'>{nickname}</div>
              )}
              
            </div>
            <div className='user-top-info-email'>{userEmail}</div>
          </div>
        </div>
      </div>
    );
  };

  //          component: 유저 하단화면 컴포넌트          //
  const UserBottom = () => {

    //          state: 페이지네이션 관련 상태          //
    const { 
      currentPage,
      setCurrentPage,
      currentSection,
      setCurrentSection,
      viewList,
      viewPageList,
      totalSection,
      setTotalList } = usePagination<BoardListItem>(5);

    //          state: 게시물 개수 상태          //
    const [count, setCount] = useState<number>(1);

    //          state: 게시물 리스트 상태 (임시)          //
    const [userBoardList, setUserBoardList] = useState<BoardListItem[]>([]);

    //          event handler: 사이드 카드 클릭 이벤트 처리          //
    const onSideCardClickHandler = () => {
      if (isMyPage) navigate(BOARD_PATH() + '/' + BOARD_WRITE_PATH())
      else if (loginUser) navigate(USER_PATH(loginUser.email))
    
    }

    //          effect: userEmail path variable이 변경될 때마다 실행될 함수          //
    useEffect(() => {
      if (!userEmail) return;
      getUserBoardListRequest(userEmail).then(getUserBoardListResponse);
    }, [userEmail])

    //          function: getUserBoardListResponse 처리 함수          //
    const getUserBoardListResponse = (responseBody: GetUserBoardListResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'NU') {
        alert('존재하지 않는 유저입니다.');
        navigate(MAIN_PATH());
        return;
      }
      if (code === 'DBE') alert('데이터베이스 오류입니다');
      if (code !== 'SU') return;

      const { userBoardList } = responseBody as GetUserBoardListResponseDto
      setTotalList(userBoardList);
      setCount(userBoardList.length);
    }


    //          render: 유저 하단화면 컴포넌트 렌더링          //
    return (
      <div id='user-bottom-wrapper'>
        <div className='user-bottom-container'>
          
          <div className='user-bottom-title'>{ isMyPage ? '내 게시물 ' : '게시물'}<span className='emphasis'>{count}</span></div>
          <div className='user-bottom-contents-box'>
            {
              count == 0 ? 
              <div className='user-bottom-contents-nothing'>{'게시물이 없습니다'}</div> :
              <div className='user-bottom-contents'>
                {viewList.map(userBoardItem => <BoardItem boardListItem={userBoardItem} />)}
              </div>
            }
            <div className='user-bottom-side-box'>
              <div className='user-bottom-side-card' onClick={onSideCardClickHandler}>
                <div className='user-bottom-side-container'>
                  {isMyPage ?
                  <>
                  <div className='icon-box'>
                    <div className='icon edit-icon'></div>
                  </div>
                  <div className='user-bottom-side-text'>{'글쓰기'}</div>
                  </> :
                  <>
                  <div className='user-bottom-side-text'>{'내 게시물로 가기'}</div>
                  <div className='icon-box'>
                    <div className='icon arrow-right-icon'></div>
                  </div>
                  </>
                  }
                </div>
              </div>
            </div>
          </div>
          <div className='user-bottom-pagination-box'>
          {count !== 0 && 
            <Pagination
            currentPage={currentPage}
            currentSection={currentSection}
            setCurrentPage={setCurrentPage}
            setCurrentSection={setCurrentSection}
            viewPageList={viewPageList}
            totalSection={totalSection}/>
            }
          </div>
        </div>
      </div>
    );
  };

  //          render: 유저 화면 컴포넌트 렌더링          //
  return (
    <>
    <UserTop/>
    <UserBottom/>
    </>
  )
}
