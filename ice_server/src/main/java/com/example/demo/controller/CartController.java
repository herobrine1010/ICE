package com.example.demo.controller;


import com.example.demo.dao.ChartMapper;
import com.example.demo.dao.GamesMapper;
import com.example.demo.entity.*;

import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ChartMapper chartMapper;
    @Autowired
    private GamesMapper gamesMapper;


    @RequestMapping(value="/getMyCart",method=RequestMethod.GET)
    public Response<CartItem> getMyCart(
            @RequestParam(value="from",required = false,defaultValue = "0")int from,
            @RequestParam(value="to",required=false,defaultValue = "100") int to,
            @RequestParam(value="reverse",required = false,defaultValue = "1") int reversed,
            HttpSession session){
        Response<CartItem> response = new Response<>();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            List<CartItem> resultList=chartMapper.getMyCart(thisUserId,from,to-from,reversed);
            response.setResult(resultList);
            response.setStatus("200");
            if(resultList.isEmpty()){response.setError("No record in database!");}
        }catch(Exception e){
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    @RequestMapping(value="/addToCart",method=RequestMethod.GET)
    public Response addToCart(
            @RequestParam("gameId")int gameId,
            @RequestParam("consoleId")int consoleId,
            HttpSession session ){
        Response response = new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            chartMapper.addToCart(thisUserId,gameId,consoleId);
            response.setStatus("200");
            response.setError("添加到购物车成功!");
        }
        catch(Exception e) {
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    @RequestMapping(value="/removeFromCart",method=RequestMethod.GET)
    public Response removeFromCart(
            @RequestParam("gameId")int gameId,HttpSession session){
        Response response = new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try {
            chartMapper.deleteByPrimaryKey(gameId, thisUserId);
            response.setStatus("200");
            response.setError("已成功从购物车移除!");
        }
        catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }




}
