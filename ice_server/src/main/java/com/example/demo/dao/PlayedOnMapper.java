package com.example.demo.dao;

import com.example.demo.entity.PlayedOn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
@Mapper
@Repository
public interface PlayedOnMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("consoleId") Integer consoleId);

    int insert(PlayedOn record);

    int insertSelective(PlayedOn record);
}