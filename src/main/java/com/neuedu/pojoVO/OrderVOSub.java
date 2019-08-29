package com.neuedu.pojoVO;

import java.math.BigDecimal;
import java.util.List;

public class OrderVOSub {
    private List<OrderItemVO> orderItemVoList;
    private BigDecimal productTotalPrice;

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public List<OrderItemVO> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVO> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }
}
