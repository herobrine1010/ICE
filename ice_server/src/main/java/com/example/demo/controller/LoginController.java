package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.demo.dao.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@RestController
public class LoginController {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response Login(@RequestBody Users user, HttpSession session) {
        Response response = new Response();

        if (userService.Exist(user.getUserName()).getStatus() != "OK") {
            return userService.Exist(user.getUserName());
        }

        List<Users> users = usersMapper.selectUser(user);
        if (users.size() == 0) {
            response.setStatus("403");
            response.setError("请输入正确的密码！");
            return response;
        }

        session.setMaxInactiveInterval(-1);

        session.setAttribute("id", users.get(0).getUserId());
        response.setStatus("200");

        response.setError(session.getId());
        return response;
    }

    //登出并终止session
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response Logout(HttpSession session) {
        //System.out.println(session.getMaxInactiveInterval());
        Response response = new Response();
        session.removeAttribute("id");
        response.setStatus("200");
        return response;
    }

    //注册部分。用户名和密码非空的校验麻烦前端写一下~
    @RequestMapping(value = "/register",method =RequestMethod.POST)
    public Response Register(@RequestBody Users user){
        Response response = new Response();
        //如果已经存在用户名，
        if (userService.Exist(user.getUserName()).getStatus() == "OK") {
            response.setStatus("200");
            response.setError("用户名已存在，请重新注册！");
            return response;
        }

        try {
            usersMapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("信息格式错误，请检查信息格式！");
            return response;
        }
        response.setStatus("200");
        response.setError("注册成功");

        return response;


    }

}
