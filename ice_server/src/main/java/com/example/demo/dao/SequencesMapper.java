package com.example.demo.dao;

import com.example.demo.entity.Sequences;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SequencesMapper {
    int deleteByPrimaryKey(String sequenceName);

    int insert(Sequences record);

    int insertSelective(Sequences record);

    Sequences selectByPrimaryKey(String sequenceName);

    int updateByPrimaryKeySelective(Sequences record);

    int updateByPrimaryKey(Sequences record);
}