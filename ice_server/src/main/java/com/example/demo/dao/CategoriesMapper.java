package com.example.demo.dao;

import com.example.demo.entity.Categories;

public interface CategoriesMapper {
    int deleteByPrimaryKey(Integer cateId);

    int insert(Categories record);

    int insertSelective(Categories record);

    Categories selectByPrimaryKey(Integer cateId);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);
}