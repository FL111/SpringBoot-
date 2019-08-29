package com.neuedu.sevice;

import com.neuedu.common.ServerResponse;
import com.neuedu.exception.MyException;

import com.neuedu.modul.PageModul;
import com.neuedu.pojo.Product;

import java.util.List;

public interface IProductService {

    public int addProduct(Product product) throws MyException;
    /**
     * 删除类别
     * */
    public int deleteProduct(int productId) throws MyException;
    /**
     * 修改类别
     * */
    public int updateProduct(Product product) throws MyException;
    /**
     * 查询类别
     * */
    public List<Product> findAll() throws MyException;


    /**
     * 根据类别id查询类别信息
     * */

    public Product findProductById(int productId);

    public int updateStatus(Product product);


    public ServerResponse findXXX(int pageNum,int pageSize,int categoryId,String keyword,String orderBy);

    public int getCount();

    public List<Product> findProductByIsNew();
    public List<Product> findProductByIsHot();
    public List<Product> findProductByIsBanner();

    public ServerResponse findProductByName(String name,Integer pageNum,Integer pageSize);
    public ServerResponse findProductByIds(Integer id,Integer pageNum,Integer pageSize);

}
