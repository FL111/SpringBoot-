package com.neuedu.sevice;

import com.neuedu.common.ServerResponse;

public interface ICartService {

    ServerResponse findListByUserid(int userid);

    ServerResponse updateProductCount(int userid,int productid,int count);

    ServerResponse addProductCount(int userid,int productid,int count);

    ServerResponse deleteProducts(int[] products,int userid);

    ServerResponse selectedProduct(int userid,int productId);

    ServerResponse unselectProduct(int userid,int productId);

    ServerResponse getCount(int userid);

    ServerResponse selectAll(int userid);

    ServerResponse unselectAll(int userid);
}
