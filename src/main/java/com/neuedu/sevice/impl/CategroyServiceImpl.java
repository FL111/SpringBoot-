package com.neuedu.sevice.impl;


import com.neuedu.dao.CategoryMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.sevice.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategroyServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int addCategory(Category category) throws MyException {
        int count =categoryMapper.insert(category);
        if(count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteCategory(int categoryId) throws MyException {
        int count=categoryMapper.deleteByPrimaryKey(categoryId);
        if(count>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateCategory(Category category) throws MyException {
        return categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public List<Category> findAll() throws MyException {


        return categoryMapper.selectAll();


    }

    @Override
    public Category findCategoryById(int categoryId) {

        return  categoryMapper.selectByPrimaryKey(categoryId);

    }

    @Override
    public int getCount() {
        return categoryMapper.getCount();
    }

    @Override
    public List<Category> findCategoryByParentid(int categoryId) {
        List<Category> categories= categoryMapper.findCategoryByParentid(categoryId);

        return categories;
    }
    List<Integer> categoryList1=new ArrayList<>();
    @Override
    public List<Integer> findDeepCategory(int categoryId) {
        categoryList1.add(categoryId);
        getCategoryList1(categoryId);
        return categoryList1;
    }

    public void getCategoryList1(int categoryId) {
        List<Category> categories=categoryMapper.findCategoryByParentid(categoryId);
        if(categories.size()!=0){
            for(Category category:categories){
                categoryList1.add(category.getId());
                getCategoryList1(category.getId());
            }
        }
    }
}