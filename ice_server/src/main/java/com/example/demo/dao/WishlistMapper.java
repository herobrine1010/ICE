package com.example.demo.dao;

import com.example.demo.entity.Wishlist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistMapper {
    int deleteByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int insert(Wishlist record);

    int insertSelective(Wishlist record);

    Wishlist selectByPrimaryKey(@Param("gameId") Integer gameId, @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(Wishlist record);

    int updateByPrimaryKey(Wishlist record);
}