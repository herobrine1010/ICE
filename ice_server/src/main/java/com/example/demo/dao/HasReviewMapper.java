package com.example.demo.dao;

import com.example.demo.entity.HasReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface HasReviewMapper {
    int deleteByPrimaryKey(@Param("reviewId") Integer reviewId, @Param("gameId") Integer gameId);

    int insert(HasReview record);

    int insertSelective(HasReview record);
}