import { Outlet, useLocation } from 'react-router-dom'
import Footer from 'layouts/footer'
import React from 'react'
import { AUTH_PATH } from 'constant';
import Header from 'layouts/Header';


//          component: 컨테이너 레이아웃          //
export default function Container() {

  //          state: 현재 페이지 path name 상태          //
  const { pathname } = useLocation();

  //          render: 컨테이너 레이아웃 헨더링          //
  return (
    <>
    <Header />
    <Outlet></Outlet>
    {pathname !== AUTH_PATH() && <Footer />}
    </>
  )
}
