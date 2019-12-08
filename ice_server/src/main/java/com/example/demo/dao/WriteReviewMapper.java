package com.example.demo.dao;

import com.example.demo.entity.ReviewsDetailed;
import com.example.demo.entity.WriteReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WriteReviewMapper {
    int deleteByPrimaryKey(Integer reviewId);

    int insert(WriteReview record);

    int insertSelective(WriteReview record);

    WriteReview selectByPrimaryKey(Integer reviewId);

    int updateByPrimaryKeySelective(WriteReview record);

    int updateByPrimaryKey(WriteReview record);

    int getWhetherCommented(@Param("userId") int userId, @Param("gameId") int gameId);

    List<ReviewsDetailed> selectAllComment(@Param("gameId") int gameId, @Param("number")int number, @Param("reverse")int reverse);

}