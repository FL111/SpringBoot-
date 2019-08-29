package com.neuedu.modul;

import java.math.BigDecimal;
import java.util.List;

public class CartModul<T> {
    private boolean allChecked;
    private BigDecimal cartTotalPrice;
    private List<T> cartProductVoList;

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public List<T> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<T> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }
}
