package com.neuedu.sevice.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ProductMapper;
import com.neuedu.exception.MyException;

import com.neuedu.modul.PageModul;
import com.neuedu.pojo.Product;
import com.neuedu.pojoVO.ProductVO;
import com.neuedu.sevice.IProductService;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    ProductMapper productMapper;
    @Override
    public int addProduct(Product product) throws MyException {
       if (product==null){
           throw new MyException("参数不能为空");
       }
       /**
        * 此处忽略逻辑处理，默认所有参数都传进来了
        *
        * */
        int count=productMapper.insert(product);
        if (count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteProduct(int productId) throws MyException {
        if (productId==0){
            throw new MyException("未知错误");
        }
        int count=productMapper.deleteByPrimaryKey(productId);
        if (count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateProduct(Product product) throws MyException {
        if (product==null){
            throw new MyException("参数不能为空");
        }
        int count= 0;
        if(product.getMainImage().equals("")&&!product.getSubImages().equals("")){
            count=productMapper.updateByMain(product);
        }else if (!product.getMainImage().equals("")&&product.getSubImages().equals("")){
            count=productMapper.updateBySub(product);
        }else if(product.getMainImage().equals("")&&product.getSubImages().equals("")){
            count=productMapper.updateByAll(product);
        }else {
            count=productMapper.updateByPrimaryKey(product);
        }
        if (count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Product> findAll() throws MyException {
        return productMapper.selectAll();
    }

    @Override
    public Product findProductById(int productId) {
        Product product=productMapper.selectByPrimaryKey(productId);
        return product;
    }

    @Override
    public int updateStatus(Product product) {
        int count=productMapper.updateStatus(product);
        if(count>0){
            return 1;
        }
        return 0;
    }
    @Override
    public ServerResponse findXXX(int pageNum,int pageSize,int categoryId,String keyword,String orderBy) {
        keyword="%"+keyword+"%";
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Product> products=productMapper.selectAllByPage(categoryId,keyword,orderBy);
        if(products.size()==0){
            return ServerResponse.createServerResponseByFailure(1,"参数错误");
        }
        PageInfo pageInfo=new PageInfo(page);
        pageInfo.setList(products);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }

    @Override
    public int getCount() {
        return productMapper.getCount();
    }

    @Override
    public List<Product> findProductByIsNew() {
        List<Product> products=productMapper.selectAllByNew();
        return products;
    }

    @Override
    public List<Product> findProductByIsHot() {
        List<Product> products=productMapper.selectAllByHot();
        return products;
    }

    @Override
    public List<Product> findProductByIsBanner() {
        List<Product> products=productMapper.selectAllByBanner();
        return products;
    }

    @Override
    public ServerResponse findProductByName(String name, Integer pageNum, Integer pageSize) {
        name="%"+name+"%";
        PageModul pageModul=new PageModul(pageNum,pageSize);
        pageModul.setOrderBy(null);
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<Product> products=productMapper.findProductByName(name);
        List<ProductVO> productVOS= Lists.newArrayList();
        for(Product product:products){
            ProductVO productVO=new ProductVO();
            productVO.setId(product.getId());
            productVO.setCategoryId(product.getCategoryId());
            productVO.setMainImage(product.getMainImage());
            productVO.setName(product.getName());
            productVO.setPrice(product.getPrice());
            productVO.setSubtitle(product.getSubtitle());
            productVOS.add(productVO);
        }
        pageModul.setPageList(productVOS);
        return ServerResponse.createServerResponseBySuccess(pageModul);
    }

    @Override
    public ServerResponse findProductByIds(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageModul pageModul=new PageModul(pageNum,pageSize);
        pageModul.setOrderBy(null);
        Product product=productMapper.selectByPrimaryKey(id);
        List<ProductVO> productVOS= Lists.newArrayList();
        ProductVO productVO=new ProductVO();
        productVO.setId(product.getId());
        productVO.setCategoryId(product.getCategoryId());
        productVO.setMainImage(product.getMainImage());
        productVO.setName(product.getName());
        productVO.setPrice(product.getPrice());
        productVO.setSubtitle(product.getSubtitle());
        productVOS.add(productVO);
        pageModul.setPageList(productVOS);
        return ServerResponse.createServerResponseBySuccess(pageModul);
    }
}
