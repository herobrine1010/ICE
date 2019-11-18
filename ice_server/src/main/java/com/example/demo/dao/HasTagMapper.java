package com.example.demo.dao;

import com.example.demo.entity.HasTag;
import org.apache.ibatis.annotations.Param;

public interface HasTagMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("tagId") Integer tagId);

    int insert(HasTag record);

    int insertSelective(HasTag record);
}