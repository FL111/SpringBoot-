package com.neuedu.sevice.impl;

import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.modul.CartModul;
import com.neuedu.modul.PageModul;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.pojoVO.CartProductVO;
import com.neuedu.sevice.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService{

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public ServerResponse findListByUserid(int userid) {
        List<CartProductVO> cartProductVOS= cartMapper.findCartDetailByUserid(userid);
        CartModul cartModul=new CartModul();
        BigDecimal total=new BigDecimal(0);
        if (cartProductVOS.size()==0){
            return ServerResponse.createServerResponseByFailure(1,"还没选中任何商品哦");
        }
        for (CartProductVO cartProductVO:cartProductVOS){
            BigDecimal x=new BigDecimal(cartProductVO.getQuantity());
            cartProductVO.setProductTotalPrice(cartProductVO.getProductPrice().multiply(x));
            if (cartProductVO.getProductChecked()!=0){
                total=total.add(cartProductVO.getProductTotalPrice());
            }
            if (cartProductVO.getQuantity()>10){
                cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");
            }else {
                cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
            }
        }
        cartModul.setCartProductVoList(cartProductVOS);
        cartModul.setCartTotalPrice(total);
        cartModul.setAllChecked(true);
        for (CartProductVO cartProductVO:cartProductVOS){
            if (cartProductVO.getProductChecked()==0){
                cartModul.setAllChecked(false);
                break;
            }
        }
        return ServerResponse.createServerResponseBySuccess(cartModul);
    }

    @Override
    public ServerResponse updateProductCount(int userid, int productid, int count) {
        int result=cartMapper.findByUseridAndProductid(userid,productid);
        if (result==0||count>10){
            return ServerResponse.createServerResponseByFailure(2,"更新数据失败");
        }
        int result1=cartMapper.updateByUseridAndProduct(userid,productid,count);
        if (result1==0){
            return ServerResponse.createServerResponseByFailure(2,"更新数据失败");
        }
        return findListByUserid(userid);
    }

    @Override
    public ServerResponse addProductCount(int userid, int productid, int count) {
        Product product=productMapper.selectByPrimaryKey(productid);
        if (product==null||count<=0||count>10){
            return ServerResponse.createServerResponseByFailure(2,"更新数据失败");
        }
        Cart cart=new Cart();
        cart.setUserId(userid);
        cart.setProductId(productid);
        cart.setQuantity(count);
        cart.setChecked(1);
        int result=cartMapper.insert(cart);
        if(result<=0){
            return ServerResponse.createServerResponseByFailure(2,"更新数据失败");
        }
        return findListByUserid(userid);
    }

    @Override
    public ServerResponse deleteProducts(int[] products,int userid) {
        for (int i=0;i<products.length;i++){
            Product product=productMapper.selectByPrimaryKey(products[i]);
            if(product==null){
                return ServerResponse.createServerResponseByFailure(3,"商品不存在");
            }
        }
        for (int i=0;i<products.length;i++){
            int result=cartMapper.findByUseridAndProductid(userid,products[i]);
            if(result==0){
                return ServerResponse.createServerResponseByFailure(3,"商品不存在");
            }
        }
        for (int i=0;i<products.length;i++){
            cartMapper.deleteByUseridAndProductID(userid,products[i]);
        }

        return findListByUserid(userid);
    }

    @Override
    public ServerResponse selectedProduct(int userid, int productId) {
        int result=cartMapper.findByUseridAndProductid(userid,productId);
        if(result==0){
            return ServerResponse.createServerResponseByFailure(3,"商品不存在");
        }
        cartMapper.checkedProduct(userid,productId);
        return findListByUserid(userid);
    }

    @Override
    public ServerResponse unselectProduct(int userid, int productId) {
        int result=cartMapper.findByUseridAndProductid(userid,productId);
        if(result==0){
            return ServerResponse.createServerResponseByFailure(3,"商品不存在");
        }
        cartMapper.uncheckProduct(userid,productId);
        return findListByUserid(userid);
    }

    @Override
    public ServerResponse getCount(int userid) {
        List<Integer> integers=cartMapper.getCartPCount(userid);
        if (integers.size()==0){
            return ServerResponse.createServerResponseByFailure(10,"出现异常");
        }
        int x=0;
        for (Integer i:integers){
            x=x+i;
        }
        return ServerResponse.createServerResponseBySuccess(x);
    }

    @Override
    public ServerResponse selectAll(int userid) {
        int count=cartMapper.checkedAll(userid);
        if (count<=0){
            return ServerResponse.createServerResponseByFailure(3,"商品不存在");
        }
        return findListByUserid(userid);
    }
    @Override
    public ServerResponse unselectAll(int userid) {
        int count=cartMapper.uncheckedAll(userid);
        if (count<=0){
            return ServerResponse.createServerResponseByFailure(3,"商品不存在");
        }
        return findListByUserid(userid);
    }
}
