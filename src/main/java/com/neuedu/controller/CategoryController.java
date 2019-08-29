package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.UserInfo;
import com.neuedu.sevice.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @RequestMapping("/get_category.do")
    public ServerResponse get(@RequestParam("categoryId")int categoryId,
                              HttpSession session){
        ServerResponse serverResponse=null;
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return serverResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        List<Category> categoryList=categoryService.findCategoryByParentid(categoryId);
        if(categoryList.size()==0){
            return serverResponse.createServerResponseByFailure(1,"未找到该品类");
        }else {
            return serverResponse.createServerResponseBySuccess(categoryList);
        }
    }
    @RequestMapping("/get_deep_category.do")
    public ServerResponse deepselect(@RequestParam("categoryId")int categoryId,
                                     HttpSession session){
        ServerResponse serverResponse=null;
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo.getRole()!=0){
            return serverResponse.createServerResponseByFailure(1,"无权限");
        }
        List<Integer> categoryList=categoryService.findDeepCategory(categoryId);
        return serverResponse.createServerResponseBySuccess(categoryList);
    }


}
