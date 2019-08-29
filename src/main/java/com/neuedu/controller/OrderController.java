package com.neuedu.controller;

import com.alibaba.druid.util.HttpClientUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.UserInfo;
import com.neuedu.sevice.IOrderService;
import net.minidev.json.JSONObject;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;



    @RequestMapping("/pay.do")
    public ServerResponse pay(@RequestParam("orderNo")Long orderNo,
                      HttpSession session)throws AlipayApiException, IOException{
        return  orderService.pay(orderNo);
    }
    @RequestMapping("/query_order_pay_status.do")
    public ServerResponse query(@RequestParam("orderNo")Long orderNo,
                                HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        return orderService.query(orderNo,userInfo.getId());
    }
    @RequestMapping("/alipay_callback.do")
    public ServerResponse callback(HttpServletRequest request){
        Map<String,String[]> params=request.getParameterMap();
        Map<String,String> requestparams= Maps.newHashMap();
        Iterator<String> it=params.keySet().iterator();
        while(it.hasNext()){
            String key=it.next();
            String[] strArr=params.get(key);
            String value="";
            for (int i=0;i<strArr.length;i++){
                value=(i==strArr.length-1)?value+strArr[i]:value+strArr[i]+",";
            }
            requestparams.put(key,value);
        }
        try {
            requestparams.remove("sign_type");
            Boolean result=AlipaySignature.rsaCheckV2(requestparams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if (!result){
                return ServerResponse.createServerResponseByFailure(1,"非法请求，验证不通过");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;//orderService.changeStatus(requestparams);

    }




    @RequestMapping("/create.do")
    public ServerResponse create(@RequestParam("shippingId")int shippingid,
                                  HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        int userid=userInfo.getId();
        //
        long time=new Date().getTime();
        time=time+userid;
        //
        return orderService.createOrder(shippingid,userid,time);
    }
    @RequestMapping("/list.do")
    public ServerResponse list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                               HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if(userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录,请登录");
        }
        if (userInfo.getRole()==1){
            return ServerResponse.createServerResponseByFailure(1,"没有权限");
        }
        return orderService.selectByUserId(userInfo.getId(),pageNum,pageSize);
    }
    @RequestMapping("/get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if(userInfo==null){
            return ServerResponse.createServerResponseByFailure(1,"用户未登录");
        }
        return orderService.selectCartByUser(userInfo.getId());
    }
    @RequestMapping("/detail.do")
    public ServerResponse detail(@RequestParam("orderNo")Long orderNo){
        return orderService.getOrderDetail(orderNo);
    }
    @RequestMapping("/cancel.do")
    public ServerResponse cancel(@RequestParam("orderNo")Long orderNo,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        return orderService.cancelOrder(orderNo,userInfo.getId());
    }




    @RequestMapping("/manage/list.do")
    public ServerResponse m_list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录,请登录");
        }
        if (userInfo.getRole()==1){
            return ServerResponse.createServerResponseByFailure(1,"无权访问");
        }
        return orderService.m_getList(pageNum,pageSize);
    }
    @RequestMapping("/manage/search.do")
    public ServerResponse m_search(@RequestParam("orderNo")Long orderNo){
        return orderService.m_findOrder(orderNo);
    }
    @RequestMapping("/manage/detail.do")
    public ServerResponse m_detail(@RequestParam("orderNo")Long orderNo){
        return orderService.m_detailOrder(orderNo);
    }
    @RequestMapping("/manage/send_goods.do")
    public ServerResponse m_send(@RequestParam("orderNo")Long orderNo){
        return orderService.m_sentOrder(orderNo);
    }





}
