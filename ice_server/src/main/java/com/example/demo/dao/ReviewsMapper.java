package com.example.demo.dao;

import com.example.demo.entity.Reviews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReviewsMapper {
    int deleteByPrimaryKey(Integer reviewId);

    int insert(Reviews record);

    int insertSelective(Reviews record);

    Reviews selectByPrimaryKey(Integer reviewId);

    int updateByPrimaryKeySelective(Reviews record);

    int updateByPrimaryKeyWithBLOBs(Reviews record);
}