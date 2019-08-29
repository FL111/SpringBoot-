package com.neuedu.sevice;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Order;

import java.lang.invoke.SerializedLambda;
import java.util.Map;

public interface IOrderService {
    ServerResponse createOrder(int shippingid,int userid,Long orderNo);

    ServerResponse selectByUserId(int userid,int pageNum,int pageSize);

    ServerResponse selectCartByUser(int userid);

    ServerResponse getOrderDetail(Long orderNo);

    ServerResponse cancelOrder(Long orderNo,int userid);

    ServerResponse m_getList(int pageNum,int pageSize);

    ServerResponse m_findOrder(Long orderNo);

    ServerResponse m_detailOrder(Long orderNo);

    ServerResponse m_sentOrder(Long orderNo);

    ServerResponse query(Long orderNo,int userid);

    ServerResponse pay(Long orderNo);

    ServerResponse changeStatus(Map<String,String> map);
}
