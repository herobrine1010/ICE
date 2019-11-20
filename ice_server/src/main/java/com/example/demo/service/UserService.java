package com.example.demo.service;

import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UsersMapper usersMapper;

    public Response Exist(String userName){
        Response response=new Response();
        Users user=new Users();
        user.setUserName(userName);
        if(usersMapper.selectUser(user).size()==0){
            response.setStatus("false");
            response.setError("用户不存在");
            return response;
        }
        response.setStatus("OK");
        return response;

    }

}
