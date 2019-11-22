package com.example.demo.dao;

import com.example.demo.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
//@Component(value="UsersMapper")
public interface UsersMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKeyWithBLOBs(Users record);

    int updateByPrimaryKey(Users record);
//----------新增函数
    List<Users> selectUser(Users user);
    //任意字段精确查找
    void deleteUser(Users user);

    void insertUser(Users user);

    void updateUser(Users user);

    List<Users> SELECT();

    List<Users> selectUsers(Users user);
    //任意字段模糊查找

}