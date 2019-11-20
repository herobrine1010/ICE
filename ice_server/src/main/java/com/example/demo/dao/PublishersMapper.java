package com.example.demo.dao;

import com.example.demo.entity.Publishers;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishersMapper {
    int deleteByPrimaryKey(Integer publisherId);

    int insert(Publishers record);

    int insertSelective(Publishers record);

    Publishers selectByPrimaryKey(Integer publisherId);

    int updateByPrimaryKeySelective(Publishers record);

    int updateByPrimaryKeyWithBLOBs(Publishers record);

    int updateByPrimaryKey(Publishers record);
}