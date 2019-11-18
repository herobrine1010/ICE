package com.example.demo.dao;

import com.example.demo.entity.Games;

public interface GamesMapper {
    int deleteByPrimaryKey(Integer gameId);

    int insert(Games record);

    int insertSelective(Games record);

    Games selectByPrimaryKey(Integer gameId);

    int updateByPrimaryKeySelective(Games record);

    int updateByPrimaryKeyWithBLOBs(Games record);

    int updateByPrimaryKey(Games record);
}