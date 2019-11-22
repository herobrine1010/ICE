package com.example.demo.dao;

import com.example.demo.entity.Consoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
@Mapper
@Repository
public interface ConsolesMapper {
    int deleteByPrimaryKey(Integer consoleId);

    int insert(Consoles record);

    int insertSelective(Consoles record);

    Consoles selectByPrimaryKey(Integer consoleId);

    int updateByPrimaryKeySelective(Consoles record);

    int updateByPrimaryKey(Consoles record);
}