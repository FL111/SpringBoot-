package com.neuedu.controller;

import com.neuedu.common.GuavaUtils;
import com.neuedu.common.ServerResponse;
import com.neuedu.config.AppConfig;
import com.neuedu.pojo.UserInfo;
import com.neuedu.sevice.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/login.do")
    public ServerResponse hello(@RequestParam(value = "username",required = false)String username,
                                @RequestParam(value = "password",required = false)String password,HttpSession session){
        ServerResponse serverResponse=null;
        UserInfo userInfo=new UserInfo(username,password);
        if (userInfo.getUsername()==null||userInfo.getUsername().equals("")){
            serverResponse=serverResponse.createServerResponseByFailure(1,"用户名为空");
        }else if(userInfo.getPassword()==null||userInfo.getPassword().equals("")){
            serverResponse=serverResponse.createServerResponseByFailure(2,"密码为空");
        }else{
            UserInfo userInfo1=userService.login(userInfo);
            if (userInfo1!=null){
                session.setAttribute("userinfo",userInfo1);
                serverResponse=ServerResponse.createServerResponseBySuccess(userInfo1);
                return serverResponse;
            }else{
                serverResponse=serverResponse.createServerResponseByFailure(404,"用户不存在");
            }
        }
        return serverResponse;
    }


    @RequestMapping("/res")
    public ServerResponse get(){
        return ServerResponse.createServerResponseBySuccess(null,"hello");
    }

    @RequestMapping("/register.do")
    public ServerResponse register(UserInfo userInfo){
        ServerResponse serverResponse=null;
        if (userInfo.getUsername()==null||userInfo.getPassword()==null||userInfo.getRole()==null||userInfo.getEmail()==null||userInfo.getAnswer()==null
                ||userInfo.getQuestion()==null){
            return serverResponse.createServerResponseByFailure(100,"注册信息不能为空");
        }
        int count=userService.register(userInfo);
        if(count==0){
            return  serverResponse.createServerResponseByFailure(1,"用户已存在");
        }else if(count==1){
            return serverResponse.createServerResponseBySuccess("成功");
        }else if (count==2){
            return serverResponse.createServerResponseByFailure(2,"未知错误");
        }else if(count==3){
            return serverResponse.createServerResponseByFailure(3,"邮箱已存在");
        }
        return serverResponse;
    }
    @RequestMapping("/check_valid.do")
    public ServerResponse check(String str,String type){
        ServerResponse serverResponse=null;
        int count=userService.check(str,type);
        if (count==1){
            return serverResponse.createServerResponseByFailure(1,"用户名已存在");
        }else if (count==2){
            return serverResponse.createServerResponseByFailure(2,"邮箱已存在");
        }else {
            return serverResponse.createServerResponseBySuccess("校验成功");
        }
    }

    @RequestMapping("/get_user_info.do")
    public ServerResponse get(HttpSession session){
        ServerResponse serverResponse=null;
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return serverResponse.createServerResponseByFailure(1,"用户未登录，无法获取当前用户信息");
        }
        userInfo=userService.findUserByUsernameAndPassword(userInfo);
        return serverResponse.createServerResponseBySuccess(userInfo);
    }

    @RequestMapping("/forget_get_question.do")
    public ServerResponse forget(String username){
        ServerResponse serverResponse=null;
        if (username==null||username.equals("")){
            return serverResponse.createServerResponseByFailure(100,"用户名不能为空");
        }
        UserInfo userInfo=userService.findUserByUsername(username);
        if(userInfo==null){
            return serverResponse.createServerResponseByFailure(101,"用户名不存在");
        }else if(userInfo.getQuestion()==null||userInfo.getQuestion().equals("")){
            return serverResponse.createServerResponseByFailure(1,"该用户未设置找回密码问题");
        }else{
            return serverResponse.createServerResponseBySuccess(userInfo.getQuestion());
        }
    }

    @RequestMapping("/forget_check_answer.do")
    public ServerResponse check_answer(String username,String question,String answer,HttpSession session){
        ServerResponse serverResponse=null;
        if (username==null||username.equals("")){
            return serverResponse.createServerResponseByFailure(100,"用户名不能为空");
        }else if(question==null||question.equals("")){
            return serverResponse.createServerResponseByFailure(100,"问题不能为空");
        }else if(answer==null||answer.equals("")){
            return serverResponse.createServerResponseByFailure(100,"答案不能为空");
        }
        int count=userService.checkNew(username,answer);
        if(count==0){
            return serverResponse.createServerResponseByFailure(1,"问题答案错误");
        }else {
            UUID uuid=UUID.randomUUID();
            GuavaUtils.put(username.toString(),uuid.toString());
            return serverResponse.createServerResponseBySuccess(uuid);
        }
    }

    @RequestMapping("/forget_reset_password.do")
    public ServerResponse reset(String username,String passwordNew,String forgetToken,HttpSession session){
        ServerResponse serverResponse=null;
        if(GuavaUtils.get(username)==null){
            return serverResponse.createServerResponseByFailure(103,"token已失效");
        }
        if(!GuavaUtils.get(username).equals(forgetToken)){
            return serverResponse.createServerResponseByFailure(104,"非法的token");
        }
        if (username==null||username.equals("")){
            return serverResponse.createServerResponseByFailure(100,"用户名不能为空");
        }
        if (passwordNew==null||passwordNew.equals("")){
            return serverResponse.createServerResponseByFailure(100,"密码不能为空");
        }
        UserInfo userInfo=userService.findUserByUsername(username);
        userInfo.setPassword(passwordNew);
        int count=userService.updateUserinfo(userInfo);
        if(count==0){
            return serverResponse.createServerResponseByFailure(1,"修改密码操作失败");
        }else {
            return serverResponse.createServerResponseBySuccess("修改密码成功");
        }
    }

    @RequestMapping("/reset_password.do")
    public ServerResponse login_reset(String passwordOld,String passwordNew,HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        ServerResponse serverResponse=null;
        if(userInfo==null){
            return serverResponse.createServerResponseByFailure(1,"用户未登录，无法获取当前用户信息");
        }
        if (passwordNew==null||passwordNew.equals("")||passwordOld==null||passwordOld.equals("")){
            return serverResponse.createServerResponseByFailure(100,"密码不能为空");
        }
        String password=userInfo.getPassword();
        if(!passwordOld.equals(password)){
            return serverResponse.createServerResponseByFailure(1,"旧密码输入错误");
        }
        userInfo.setPassword(passwordNew);
        userService.updateUserinfo(userInfo);
        session.setAttribute("userinfo",userInfo);
        return serverResponse.createServerResponseBySuccess("修改密码成功");
    }

    @RequestMapping("/update_information.do")
    public ServerResponse login_change(String email,String phone,String question,String answer,HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        ServerResponse serverResponse=null;
        if(userInfo==null){
            return serverResponse.createServerResponseByFailure(1,"用户未登录");
        }
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setAnswer(answer);
        userInfo.setQuestion(question);
        userService.updateUserinfo(userInfo);
        session.setAttribute("userinfo",userInfo);
        return serverResponse.createServerResponseBySuccess("更新个人信息成功");
    }

    @RequestMapping("/get_inforamtion.do")
    public ServerResponse login_get(HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        ServerResponse serverResponse=null;
        if(userInfo==null){
            return serverResponse.createServerResponseByFailure(10,"用户未登录,无法获取当前用户信息");
        }
        return serverResponse.createServerResponseBySuccess(userInfo);
    }

    @RequestMapping("/logout.do")
    public ServerResponse logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        session.setAttribute("userinfo",null);
        ServerResponse serverResponse=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("username")){
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setPath("/user");
                response.addCookie(cookie);
            }else if(cookie.getName().equals("password")){
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setPath("/user");
                response.addCookie(cookie);
            }
        }
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo!=null){
            return serverResponse.createServerResponseByFailure(1,"服务端异常");
        }else {
            return serverResponse.createServerResponseBySuccess(0,"退出成功");
        }
    }
}
