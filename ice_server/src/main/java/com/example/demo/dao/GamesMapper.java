package com.example.demo.dao;

import com.example.demo.entity.Games;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesMapper {
    int deleteByPrimaryKey(Integer gameId);

    int insert(Games record);

    int insertSelective(Games record);

    Games selectByPrimaryKey(Integer gameId);

    int updateByPrimaryKeySelective(Games record);

    int updateByPrimaryKeyWithBLOBs(Games record);

    int updateByPrimaryKey(Games record);
}