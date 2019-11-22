package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Consoles;
import com.example.demo.entity.Publishers;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Response;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainpageController {
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private ConsolesMapper consolesMapper;
    @Autowired
    private PublishersMapper publishersMapper;
    @Autowired
    private GamesMapper gamesMapper;
    @Autowired
    private HasTagMapper hasTagMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private OrdersMapper ordersMapper;

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public Response getCategories(){
        Response response = new Response();
        List<Categories> AllCate = categoriesMapper.selectAll();
        response.setResult(AllCate);

        return response;
    }

    @RequestMapping(value = "/getConsoles", method = RequestMethod.GET)
    public Response getConsoles(){
        Response response = new Response();
        List<Consoles> AllConsoles = consolesMapper.selectAll();
        response.setResult(AllConsoles);

        return response;
    }

    @RequestMapping(value = "/getPublishers", method = RequestMethod.GET)
    public Response getPublishers(){
        Response response = new Response();
        List<Publishers> AllPubs = publishersMapper.selectAll();
        response.setResult(AllPubs);

        return response;
    }

    @RequestMapping(value = "/getOrderNum", method = RequestMethod.GET)
    public Response getOderNum(HttpSession session){
        Response response = new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int thisUserId = Integer.parseInt(session.getAttribute("id").toString());
        Integer num = ordersMapper.orderNumOf(thisUserId);
        List<Integer> result = new ArrayList<Integer>();
        result.add(num);
        response.setResult(result);

        return response;
    }

    @RequestMapping(value = "/getChartNum", method = RequestMethod.GET)
    public Response getChartNum(HttpSession session){
        Response response = new Response();

        return response;
    }

    @RequestMapping(value = "/getGames", method = RequestMethod.GET)
    public Response getGames(@RequestBody Integer sortType, Integer Times){
        Response response = new Response();

        return response;
    }

}