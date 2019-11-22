package com.example.demo.dao;

import com.example.demo.entity.Belong;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BelongMapper {
    int deleteByPrimaryKey(Integer gameId);

    int insert(Belong record);

    int insertSelective(Belong record);

    Belong selectByPrimaryKey(Integer gameId);

    int updateByPrimaryKeySelective(Belong record);

    int updateByPrimaryKey(Belong record);
}