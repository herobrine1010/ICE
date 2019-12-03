package com.example.demo.dao;

import com.example.demo.entity.Games;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GamesMapper {
    int deleteByPrimaryKey(Integer gameId);

    int insert(Games record);

    int insertSelective(Games record);

    Games selectByPrimaryKey(Integer gameId);

    int updateByPrimaryKeySelective(Games record);

    int updateByPrimaryKeyWithBLOBs(Games record);

    int updateByPrimaryKey(Games record);

    List<Games> getAll();

    List<Games> searchByTitle(String keyWords);
}