package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.sevice.ICartService;
import org.apache.catalina.User;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {
    @Autowired
    ICartService cartService;

    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        int userid=userInfo.getId();
        return cartService.findListByUserid(21);
    }
    @RequestMapping("/update.do")
    public ServerResponse update(@RequestParam(value = "productId",required = false,defaultValue = "-1")Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "-1")Integer count,
                              HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        if(productId==-1||count==-1){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        return cartService.updateProductCount(userInfo.getId(),productId,count);
    }
    @RequestMapping("/add.do")
    public ServerResponse add(@RequestParam(value = "productId",required = false,defaultValue = "-1")Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "-1")Integer count,
                              HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        if(productId==-1||count==-1){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        return cartService.addProductCount(userInfo.getId(),productId,count);
    }
    @RequestMapping("/delete_product.do")
    public ServerResponse delete(@RequestParam("productids")String productIds,
                                 HttpSession session){
        String[] Ids=productIds.split(",");
        int[] idList=new int[Ids.length];
        for(int i=0;i<Ids.length;i++){
            idList[i]=Integer.parseInt(Ids[i]);
        }
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        if (idList.length==0){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        return cartService.deleteProducts(idList,userInfo.getId());
    }
    @RequestMapping("/select.do")
    public ServerResponse select(@RequestParam(value = "productId",required = false,defaultValue = "-1")Integer productId,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        if (productId==-1){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        return cartService.selectedProduct(userInfo.getId(),productId);
    }
    @RequestMapping("/un_select.do")
    public ServerResponse unselect(@RequestParam(value = "productId",required = false,defaultValue = "-1")Integer productId,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        if (productId==-1){
            return ServerResponse.createServerResponseByFailure(9,"参数不能为空");
        }
        return cartService.unselectProduct(userInfo.getId(),productId);
    }
    @RequestMapping("/get_cart_product_count.do")
    public ServerResponse  getCount(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseBySuccess("0",userInfo);
        }
        return cartService.getCount(userInfo.getId());
    }
    @RequestMapping("/select_all.do")
    public ServerResponse selectAll(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseBySuccess("0",userInfo);
        }
        return cartService.selectAll(userInfo.getId());
    }
    @RequestMapping("/un_select_all.do")
    public ServerResponse unselectAll(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseBySuccess("0",userInfo);
        }
        return cartService.unselectAll(userInfo.getId());
    }
}
