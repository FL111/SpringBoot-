package com.neuedu.sevice.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.modul.PageModul;
import com.neuedu.pojo.Shipping;
import com.neuedu.sevice.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse addAddress(Shipping shipping) {
        int count=shippingMapper.insert(shipping);
        if (count<=0){
            return ServerResponse.createServerResponseByFailure(1,"新建地址失败");
        }
        shipping=shippingMapper.getCurrentAddress(shipping);
        Map<String ,Integer> shipp=new HashMap<>();
        shipp.put("shippingId",shipping.getId());
        return ServerResponse.createServerResponseBySuccess(0,"新建地址成功",shipp);
    }

    @Override
    public ServerResponse delAddressById(Integer shippingId) {
        int count=shippingMapper.deleteByPrimaryKey(shippingId);
        if (count<=0){
            return ServerResponse.createServerResponseByFailure(1,"删除失败");
        }else{
            return ServerResponse.createServerResponseBySuccess("删除地址成功");
        }
    }

    @Override
    public ServerResponse updateAddressById(Shipping shipping) {
        int count=shippingMapper.updateByPrimaryKey(shipping);
        if (count>0){
            return ServerResponse.createServerResponseBySuccess(0,"更新地址成功");
        }
        return ServerResponse.createServerResponseByFailure(1,"更新地址失败");
    }

    @Override
    public ServerResponse selectAddress(Integer shippingid) {

        Shipping shipping=shippingMapper.selectByPrimaryKey(shippingid);
        if (shipping!=null){
            return ServerResponse.createServerResponseBySuccess(shipping);
        }else {
            return null;
        }
    }

    @Override
    public ServerResponse getAll(int pageNum, int pageSize,int userid) {

        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList=shippingMapper.selectAll(userid);
        PageInfo pageInfo=new PageInfo(page);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

}
