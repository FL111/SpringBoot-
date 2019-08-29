package com.neuedu.interceptor;

import com.neuedu.pojo.UserInfo;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        if (userInfo==null){
            return false;
        }else {
            return true;
        }

    }


}
