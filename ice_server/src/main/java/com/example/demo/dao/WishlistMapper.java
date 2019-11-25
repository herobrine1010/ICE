package com.example.demo.dao;

import com.example.demo.entity.Wishlist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@Repository
public interface WishlistMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int insert(Wishlist record);

    int insertSelective(Wishlist record);

    Wishlist selectByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(Wishlist record);

    int updateByPrimaryKey(Wishlist record);

    List<Wishlist> selectByUserId(@Param("userId")Integer userId);

    int selectByGameId(@Param("gameId") Integer gameId);
}