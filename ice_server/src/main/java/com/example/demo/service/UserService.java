package com.example.demo.service;

import com.example.demo.entity.Users;
import java.util.List;

public interface UserService {

    //新建用户
    boolean addUser(Users users);
    //用户修改个人信息
    boolean updateUserInfo(Users users);

    boolean updateUserAvatar(Users users);

    boolean logOut(String userName);

    boolean checkPassword(String userName,String password);

    boolean ifAdd(Users users);

    boolean deleteUser(Integer userId);

    List<Users> getAllUser();

    List<Users> commentUser(Integer userId);

    List<Users> userLists(String userName);

}
