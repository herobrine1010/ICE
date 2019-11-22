package com.example.demo.dao;

import com.example.demo.entity.HasTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
@Mapper
@Repository
public interface HasTagMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("tagId") Integer tagId);

    int insert(HasTag record);

    int insertSelective(HasTag record);
}