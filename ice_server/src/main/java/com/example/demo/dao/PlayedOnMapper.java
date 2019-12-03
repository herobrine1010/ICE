package com.example.demo.dao;

import com.example.demo.entity.PlayedOn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlayedOnMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("consoleId") Integer consoleId);

    int insert(PlayedOn record);

    int insertSelective(PlayedOn record);

    List<PlayedOn> selectByConsoleId(Integer consoleId);

    PlayedOn selectByPrimaryKey(@Param("gameId") Integer gameId, @Param("consoleId") Integer consoleId);

    List<PlayedOn> selectByGameId(Integer gameId);
}