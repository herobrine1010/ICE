package com.example.demo.dao;

import com.example.demo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrdersMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
}