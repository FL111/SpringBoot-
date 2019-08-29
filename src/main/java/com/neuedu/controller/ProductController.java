package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.modul.PageModul;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.sevice.ICategoryService;
import com.neuedu.sevice.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;

    @RequestMapping("/list.do")
    @CrossOrigin
    public ServerResponse list(@RequestParam(value = "categoryId") int categoryId,
                               @RequestParam(value = "keyword") String keyword,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "300")int pageSize,
                               @RequestParam(value = "orderBy",required = false,defaultValue = "DESC")String orderBy){
        PageModul pageModul=new PageModul(pageNum,pageSize);
        ServerResponse serverResponse=productService.findXXX(pageNum,pageSize,categoryId,keyword,orderBy);
        return serverResponse;
    }
    @RequestMapping("/logempty.do")
    public ServerResponse log(){
        if(true){
            return ServerResponse.createServerResponseBySuccess(0,"调用成功");
        }else {
            return ServerResponse.createServerResponseByFailure(1,null);
        }
    }
    @RequestMapping("/topcategory.do")
    public ServerResponse toCategory(@RequestParam(value = "sid",required = false,defaultValue = "0")Integer sid){
        List<Category> categories=categoryService.findCategoryByParentid(sid);
        return ServerResponse.createServerResponseBySuccess(categories);
    }
    @RequestMapping("/detail.do")
    public ServerResponse detail(@RequestParam(value = "productId",required = false,defaultValue = "-1") Integer productId,
                                 @RequestParam(value = "is_new",required = false,defaultValue = "-1") Integer is_new,
                                 @RequestParam(value = "is_hot",required = false,defaultValue = "-1")Integer is_hot,
                                 @RequestParam(value = "is_banner",required = false,defaultValue = "-1")Integer is_banner){
        if (productId==-1&&is_new==-1&&is_banner==-1&&is_hot==-1){
            return ServerResponse.createServerResponseByFailure(1,"参数错误");
        }
        if (productId!=-1){
            Product product=productService.findProductById(productId);
            if (product.getStatus()!=1){
                return ServerResponse.createServerResponseByFailure(4,"该商品已下架");
            }
            return ServerResponse.createServerResponseBySuccess(product);
        }
        if (is_hot!=-1){
            List<Product> products=productService.findProductByIsHot();
            return ServerResponse.createServerResponseBySuccess(products);
        }
        if (is_new!=-1){
            List<Product> products=productService.findProductByIsNew();
            return ServerResponse.createServerResponseBySuccess(products);
        }
        if (is_banner!=-1){
            List<Product> products=productService.findProductByIsBanner();
            return ServerResponse.createServerResponseBySuccess(products);
        }
        return ServerResponse.createServerResponseByFailure(1,"参数错误");
    }
    @RequestMapping("/search.do")
    public ServerResponse search(@RequestParam(value = "productName",required = false)String productName,
                                 @RequestParam(value = "productId",required = false)Integer productId,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                 HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute("userinfo");
        if (userInfo==null){
            return ServerResponse.createServerResponseByFailure(10,"用户未登录，请登录");
        }
        if (!productName.equals("")){
            return productService.findProductByName(productName,pageNum,pageSize);
        }else {
            return productService.findProductByIds(productId,pageNum,pageSize);
        }
    }
}
