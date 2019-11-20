package com.example.demo.dao;

import com.example.demo.entity.Consoles;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsolesMapper {
    int deleteByPrimaryKey(Integer consoleId);

    int insert(Consoles record);

    int insertSelective(Consoles record);

    Consoles selectByPrimaryKey(Integer consoleId);

    int updateByPrimaryKeySelective(Consoles record);

    int updateByPrimaryKey(Consoles record);
}