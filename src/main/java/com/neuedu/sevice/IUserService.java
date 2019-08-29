package com.neuedu.sevice;

import com.neuedu.exception.MyException;
//import com.neuedu.modul.PageModul;
import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface IUserService {

    public UserInfo login(UserInfo userInfo) throws MyException;

    public List<UserInfo> findAll() throws MyException;

    public UserInfo findUserById(int id)throws MyException;

    public int updateUserinfo(UserInfo userInfo);

    public int deleteById(int id);

    public int insertUser(UserInfo userInfo);

    public UserInfo findUserByUsernameAndPassword (UserInfo userInfo);

    //public PageModul findXXX(PageModul pageModul);

    public int getCount();

    public int register(UserInfo userInfo);

    public int check(String str,String type);

    public UserInfo findUserByUsername(String username);

    public int checkNew(String username,String answer);

}