package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.sevice.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
    @Autowired
    IShippingService shippingService;
    @RequestMapping("/add.do")
    public ServerResponse add(@RequestParam("userId")Integer userId,
                              @RequestParam("receiverName")String receiverName,
                              @RequestParam("receiverPhone")String receiverPhone,
                              @RequestParam("receiverMobile")String receiverMobile,
                              @RequestParam("receiverProvince")String receiverProvince,
                              @RequestParam("receiverCity")String receiverCity,
                              @RequestParam(value = "receiverDistrict",required = false,defaultValue = "null")String receiverDistrict,
                              @RequestParam("receiverAddress")String receiverAddress,
                              @RequestParam("receiverZip")String receiverZip){
        Shipping shipping=new Shipping(userId,receiverName,receiverPhone,receiverMobile,receiverProvince,receiverCity,receiverDistrict,receiverAddress,receiverZip);

        return shippingService.addAddress(shipping);
    }
    @RequestMapping("/del.do")
    public ServerResponse del(@RequestParam("shipping")Integer shippingId){
        return shippingService.delAddressById(shippingId);
    }
    @RequestMapping("/update.do")
    public ServerResponse update(@RequestParam("id")Integer id,
                                 @RequestParam("receiverName")String receiverName,
                                 @RequestParam("receiverPhone")String receiverPhone,
                                 @RequestParam("receiverMobile")String receiverMobile,
                                 @RequestParam("receiverProvince")String receiverProvince,
                                 @RequestParam("receiverCity")String receiverCity,
                                 @RequestParam(value = "receiverDistrict",required = false,defaultValue = "null")String receiverDistrict,
                                 @RequestParam("receiverAddress")String receiverAddress,
                                 @RequestParam("receiverZip")String receiverZip,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        Shipping shipping=new Shipping(userInfo.getId(),receiverName,receiverPhone,receiverMobile,receiverProvince,receiverCity,receiverDistrict,receiverAddress,receiverZip);
        shipping.setId(id);
        return shippingService.updateAddressById(shipping);
    }
    @RequestMapping("/select.do")
    public ServerResponse select(@RequestParam("shippingId") Integer shippingId,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(1,"请登陆之后查询");
        }
        return shippingService.selectAddress(shippingId);
    }
    @RequestMapping("/list.do")
    public ServerResponse list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                               HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(1,"请登陆之后查询");
        }
        return shippingService.getAll(pageNum,pageSize,userInfo.getId());
    }
}
