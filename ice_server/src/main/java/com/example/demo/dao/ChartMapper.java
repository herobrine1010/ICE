package com.example.demo.dao;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Chart;
import com.example.demo.entity.Games;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChartMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int insert(Chart record);

    int insertSelective(Chart record);

    Integer chartNumOf(Integer userId);

    List<CartItem> getMyCart(@Param("userId")int userId, @Param("startWith")int startWith, @Param("endWith")int endWith, @Param("reverse")int reverse);

    int addToCart(@Param("userId")int userId,@Param("gameId")int gameId,@Param("consoleId")int consoleId);
}