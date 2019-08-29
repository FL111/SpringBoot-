package com.neuedu.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class CookieUtils {
    private static final String  COOKIE_DOMAIN=".o3o.online";

    public static void writeCookie(HttpServletResponse response,String cookieName,String cookieValue){
        Cookie cookie =new Cookie(cookieName,cookieValue);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);
    }

    public String readCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies=request.getCookies();
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response,String cookieName){
        Cookie[] cookies=request.getCookies();
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals(cookieName)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
}
