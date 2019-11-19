package com.example.demo.service.impl;

import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public boolean addUser(Users users){
        return usersMapper.addUser(users);
    }

    @Override
    public boolean updateUserInfo(Users users){
        return usersMapper.updateUserInfo(users);
    }

    @Override
    public boolean updateUserAvatar(Users users){
        return usersMapper.updateUserAvatar(users);
    }

    @Override
    public boolean logOut(String userName){
        return  usersMapper.logOut(userName);
    }

    @Override
    public boolean checkPassword(String userName,String password){
        return usersMapper.checkPassword
                (userName,password);
    }
    @Override
    public boolean ifAdd(Users users){
        return usersMapper.ifAdd(users);
    }

    @Override
    public boolean deleteUser(Integer userId){
        return usersMapper.deleteUser(userId);
    }

    @Override
    public List<Users> getAllUser(){
        return usersMapper.getAllUser();
    }

    @Override
    public List<Users> commentUser(Integer userId){
        return usersMapper.commentUser(userId);
    }
    @Override
    public List<Users> userLists(String userName){
        return usersMapper.userLists(userName);
    }





}
