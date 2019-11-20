package com.example.demo.dao;

import com.example.demo.entity.Chart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int insert(Chart record);

    int insertSelective(Chart record);
}