package com.example.demo.dao;

import com.example.demo.entity.SaleGame;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SaleGameMapper {
    int deleteByPrimaryKey(@Param("publisherId") Integer publisherId, @Param("gameId") Integer gameId);

    int insert(SaleGame record);

    int insertSelective(SaleGame record);
}