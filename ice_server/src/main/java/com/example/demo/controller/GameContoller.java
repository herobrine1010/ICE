package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class GameContoller {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ChartMapper chartMapper;
    @Autowired
    private GamesMapper gamesMapper;
    @Autowired
    private PlayedOnMapper playedOnMapper;
    @Autowired
    private ConsolesMapper consolesMapper;
    @Autowired
    private HasTagMapper hasTagMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private BelongMapper belongMapper;
    @Autowired
    private PublishersMapper publishersMapper;
    @Autowired
    private SaleGameMapper saleGameMapper;

    @RequestMapping(value = "/getGameDetail",method =RequestMethod.GET)
    public Response<Games> getGameDetail(@RequestParam("gameId") int gameId, HttpSession session) {
        Response<Games> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if (!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try {
            Games g=gamesMapper.selectByPrimaryKey(gameId);
            List<Games> resultList=new ArrayList<>();
            resultList.add(g);
            response.setResult(resultList);
            response.setStatus("200");
        } catch (Exception e) {
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    @RequestMapping(value="/getGameConsole",method=RequestMethod.GET)
    public Response<Consoles> getGameConsole(@RequestParam("gameId")int gameId, HttpSession session){
        Response<Consoles> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if (!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            List<PlayedOn> list1=playedOnMapper.selectByGameId(gameId);
            List<Consoles> list2=new ArrayList<>();
            for(int i=0;i<list1.size();i++){
                list2.add(consolesMapper.selectByPrimaryKey(list1.get(i).getConsoleId()));
            }
            response.setStatus("200");
            response.setResult(list2);
        }catch(Exception e){
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    @RequestMapping(value="/getGameTag",method=RequestMethod.GET)
    public Response<Tags> getGameTag(@RequestParam("gameId")int gameId, HttpSession session){
        Response<Tags> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if (!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            List<HasTag> list1=hasTagMapper.selectByGameId(gameId);
            List<Tags> list2=new ArrayList<>();
            for(int i=0;i<list1.size();i++){
                list2.add(tagsMapper.selectByPrimaryKey(list1.get(i).getTagId()));
            }
            response.setStatus("200");
            response.setResult(list2);
        }catch(Exception e){
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    @RequestMapping(value="/getGameCate",method=RequestMethod.GET)
    public Response<Categories> getGameCate(@RequestParam("gameId")int gameId, HttpSession session){
        Response<Categories> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if (!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            Belong b=belongMapper.selectByPrimaryKey(gameId);
            List<Categories> resultList=new ArrayList<>();
            Categories c= categoriesMapper.selectByPrimaryKey(b.getCateId());
            resultList.add(c);
            response.setResult(resultList);
            response.setStatus("200");
        }catch(Exception e){
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    @RequestMapping(value="/getGamePublisher",method=RequestMethod.GET)
    public Response<Publishers> getGamePublisher(@RequestParam("gameId")int gameId, HttpSession session){
        Response<Publishers> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
        if (!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            List<SaleGame> b=saleGameMapper.selectByGameId(gameId);
            List<Publishers> resultList=new ArrayList<>();
            Publishers c= publishersMapper.selectByPrimaryKey(b.get(0).getPublisherId());
            resultList.add(c);
            response.setResult(resultList);
            response.setStatus("200");
        }catch(Exception e){
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    @RequestMapping(value = "/getPics", method = RequestMethod.GET)
    public Response getPics(@RequestParam("gameId")int gameId){
        Response response=new Response();

        String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "games" + System.getProperty("file.separator") + gameId + System.getProperty("file.separator");
        File file = new File(path);
        File[] tempList = file.listFiles();
        List<String> result=new ArrayList<>();
        for (int i = 0; i < tempList.length; i++) {
            if(tempList[i].getName().contains("cover")){
                continue;
            }
            result.add("/images/games/"+gameId+"/"+tempList[i].getName());
        }

        response.setStatus("200");
        response.setResult(result);
        return response;
    }

}
