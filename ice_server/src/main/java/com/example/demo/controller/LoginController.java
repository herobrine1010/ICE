package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.PublishersMapper;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Publishers;
import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import com.example.demo.service.PublisherService;
import com.example.demo.service.SessionService;
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
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.example.demo.dao.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sun.reflect.annotation.ExceptionProxy;

@RestController
public class LoginController {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PublishersMapper publishersMapper;
    @Autowired
    private PublisherService publisherService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<Integer> login(@RequestBody Users user, HttpSession session) {
        Response<Integer> response = new Response();

        if (!Objects.equals(userService.Exist(user.getUserName()).getStatus(), "200")) {
            return userService.Exist(user.getUserName());
        }
        //List <Users> users= usersMapper.SELECT();
        List<Users> users = usersMapper.selectUser(user);
        if (users.size() == 0) {
            response.setStatus("403");
            response.setError("Wrong password !");
            return response;
        }

        session.setMaxInactiveInterval(-1);

        session.setAttribute("id", users.get(0).getUserId());

        //System.out.println(session.getAttribute("id"));

        response.setStatus("200");
        List<Integer> l =new ArrayList<>();
        l.add(users.get(0).getUserId());
        response.setResult(l);
        response.setError(session.getId());
        return response;
    }

    @RequestMapping(value = "/publisherLogin", method = RequestMethod.POST)
    public Response publisherLogin(@RequestBody Publishers publisher, HttpSession session) {
        Response response = new Response();

        if (!Objects.equals(publisherService.Exist(publisher.getPublisherName()).getStatus(), "200")) {
            return publisherService.Exist(publisher.getPublisherName());
        }
        //List <Users> users= usersMapper.SELECT();
        Publishers publisherRecord=publishersMapper.selectByName(publisher.getPublisherName()).get(0);
        if (!publisher.getPwd().equals(publisherRecord.getPwd())) {
            response.setStatus("403");
            response.setError("Wrong password !");
            return response;
        }

        session.setMaxInactiveInterval(-1);

        session.setAttribute("id", publisherRecord.getPublisherId());

        //System.out.println(session.getAttribute("id"));

        response.setStatus("200");

        response.setError(session.getId());
        return response;
    }



    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response logout(HttpSession session) {
        //System.out.println(session.getMaxInactiveInterval());
        Response response = new Response();
        session.removeAttribute("id");
        response.setStatus("200");
        return response;
    }


    @RequestMapping(value = "/register",method =RequestMethod.POST)
    public Response register(@RequestBody Users user){
        Response response = new Response();
        //もしユーザー名は他人に使ってしまた
        if (Objects.equals(userService.Exist(user.getUserName()).getStatus(), "OK")) {
            response.setStatus("403");
            response.setError("The name you submitted was already registered. Please use a different one!");
            return response;
        }
        try {
            usersMapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        response.setStatus("200");
        response.setError("Registration successful!");

        return response;
    }

    @RequestMapping(value="/updateInfo",method=RequestMethod.POST)
    public Response updateInfo(@RequestBody Users user,HttpSession session){

        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        user.setUserId(Integer.parseInt(session.getAttribute("id").toString()));
       // 以上は必要的なコードである。（セッション確認）
       // これからの部分はユーザー·アカウントをチェックすることです。
        Users user1 =new Users();
        user1.setUserId((int) session.getAttribute("id"));
        if(usersMapper.selectUser(user1).isEmpty()){
            response.setStatus("403");
            response.setError("");
            return response;
        }
        //普段使いに省略するでも問題を齎させず。

        try {
            usersMapper.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        response.setStatus("200");
        response.setError("Account information updated successfully!");
        return response;

    }

    @RequestMapping(value="/updateAvatar",method = RequestMethod.POST)
    public Response updateAvatar(@RequestParam("file") MultipartFile avatarFile, HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }

        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        //ちょっと後で使われるべきです。

        if (avatarFile.isEmpty()) {
            response.setError("upload failed");
            response.setStatus("500");
            return response;
        }

        String fileName=avatarFile.getOriginalFilename();
        //より良い書き方：
        //String fileName = System.currentTimeMillis()+avatarFile.getOriginalFilename();

        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatarImages" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatarPath = "/avatarImages/"+fileName;
        try {
            avatarFile.transferTo(dest);
            Users user =new Users();
            user.setUserId(thisUserId);
            user.setAvatarPath(storeAvatarPath);
            usersMapper.updateUser(user);

        }
        catch (IOException e){
            response.setError("upload failed");
            response.setStatus("403");
            return response;
        }
        response.setError("upload successful!");
        response.setStatus("200");
        return response;

    }

    @RequestMapping(value="/getAddress",method=RequestMethod.POST)
    public Response<String> getAddress(HttpSession session){
        Response<String> response = new Response<>();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try {
            String s = usersMapper.selectByPrimaryKey(thisUserId).getAddress();
            String[] ss = s.split("%");
            List<String> resultList = Arrays.asList(ss);
            response.setResult(resultList);
            response.setStatus("200");
        }catch (Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    @RequestMapping(value="/updateAddress",method=RequestMethod.POST)
    public Response updateAddress(@RequestBody List<String> s, HttpSession session){
        Response response = new Response<>();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            String w="";
            for(int i=0;i<s.size();i++){
                w+=s.get(i);
                if(i!=s.size()-1) w+="%";
            }
            Users u=new Users();
            u.setUserId(thisUserId);
            u.setAddress(w);
            usersMapper.updateByPrimaryKeySelective(u);
            response.setError("修改地址成功，测试地址:"+w);
            response.setStatus("200");
        }catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

}
