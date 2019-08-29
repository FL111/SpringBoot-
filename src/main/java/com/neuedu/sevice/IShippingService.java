package com.neuedu.sevice;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IShippingService {
    ServerResponse addAddress(Shipping shipping);

    ServerResponse delAddressById(Integer shippingId);

    ServerResponse updateAddressById(Shipping shipping);

    ServerResponse selectAddress(Integer shippingid);

    ServerResponse getAll(int pageNum,int pageSize,int userid);
}
