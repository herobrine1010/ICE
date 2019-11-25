package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    //このコントローラーは主にIDを使ってゆーざーインフォを取得するの為にデザインした。

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private HasReviewMapper hasReviewMapper;
    @Autowired
    private WriteReviewMapper writeReviewMapper;
    @Autowired
    private ReviewsMapper reviewsMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;


    @RequestMapping(value="/getUser",method=RequestMethod.GET)
    public Response<Users> getUser(@RequestParam("userId") int userId,HttpSession session ){

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }

        Response<Users> response = new Response<>();

        try {
            Users result = usersMapper.selectByPrimaryKey(userId);
            if (result!=null) {
                List<Users> resultList = Arrays.asList(result);
                response.setStatus("200");
                response.setResult(resultList);
                response.setError("Got user info successfully!");
            }
            else{
                response.setStatus("404");
                response.setError("No such user!");
            }
        }
        catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

}
