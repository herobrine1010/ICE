package com.example.demo.dao;

import com.example.demo.entity.RateGame;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
@Mapper
@Repository
public interface RateGameMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int insert(RateGame record);

    int insertSelective(RateGame record);

    RateGame selectByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(RateGame record);

    int updateByPrimaryKey(RateGame record);

    int submitRate(@Param("userId")int userId,@Param("gameId")int gameId,@Param("rate")int rate);

    double getAverage(@Param("gameId")int gameId);

    int whetherRated(@Param("gameId") int gameId);

    int myRate(@Param("gameId") int gameId,@Param("userId") int userId);
}