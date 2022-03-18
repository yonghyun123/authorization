package com.caremind.test.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_ID = "LOGIN_ID";

    // 인스턴스화 방지
    private SessionUtil() {}

    public static String getLoginUserId(HttpSession session){
        return (String)session.getAttribute(LOGIN_ID);
    }

    public static void setLoginUserId(HttpSession session, int id){
        session.setAttribute(LOGIN_ID, id);
    }
}
