import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate, useParams } from "react-router-dom";

export default function OAuth() {

    const { token, expirationTime } = useParams();
    const [cookie, setCookie] = useCookies();
    const navigate = useNavigate();

    useEffect (() => {
        if (!token || !expirationTime) return;
        const now = (new Date().getTime()) * 1000;
        const expires = new Date(now + Number(expirationTime));
        setCookie('accessToken', token, { expires, path: '/'});
        navigate('/');
    }, [token]);

    return (
        <>
        <p>
            리다이렉트 중입니다.
        </p>
        </>
    )
}
