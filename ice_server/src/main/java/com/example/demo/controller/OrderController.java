package com.example.demo.controller;

import com.example.demo.dao.OrdersMapper;
import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Response;
import com.example.demo.service.OrderService;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    static private List<Orders> ordersList_0=new ArrayList<>();
    @Autowired
    static private List<Orders> ordersList_1=new ArrayList<>();
    @Autowired
    static private List<Orders> ordersList_2=new ArrayList<>();
    @Autowired
    static private List<Orders> ordersList_3=new ArrayList<>();
    @Autowired
    static private List<Orders> ordersList_4=new ArrayList<>();

    @RequestMapping(value = "/initAllOrderList", method = RequestMethod.GET)
    public Response initAllOrderList(HttpSession session){
        Response response=new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int userId = Integer.parseInt(session.getAttribute("id").toString());
        if(usersMapper.selectByPrimaryKey(userId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<Orders> allOrderList=ordersMapper.selectByUserId(userId);
        ordersList_0.clear();
        ordersList_1.clear();
        ordersList_2.clear();
        ordersList_3.clear();
        ordersList_4.clear();
        for(int i=0;i<allOrderList.size();i++){
            if(allOrderList.get(i).getStatus()==0){
                ordersList_0.add(allOrderList.get(i));
            }
            else if(allOrderList.get(i).getStatus()==1){
                ordersList_1.add(allOrderList.get(i));
            }
            else if(allOrderList.get(i).getStatus()==2){
                ordersList_2.add(allOrderList.get(i));
            }
            else if(allOrderList.get(i).getStatus()==3){
                ordersList_3.add(allOrderList.get(i));
            }
            else if(allOrderList.get(i).getStatus()==4){
                ordersList_4.add(allOrderList.get(i));
            }
        }

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/confirmOrder", method=RequestMethod.GET)
    public Response confirmOrder(@RequestParam(value = "orderId") Integer orderId){
        Response response=new Response();

        Orders record=ordersMapper.selectByPrimaryKey(orderId);
        record.setStatus(2);
        ordersMapper.updateByPrimaryKeySelective(record);
        response.setStatus("200");
        return response;
    }


    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    public Response getOrderList(@RequestParam(value = "status") Integer status,
                                 HttpSession session){
        Response response=new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int userId = Integer.parseInt(session.getAttribute("id").toString());
        if(usersMapper.selectByPrimaryKey(userId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<Orders> temp=new ArrayList<>();
        if(status==0){
            temp = ordersList_0;
        }
        else if(status==1){
            temp = ordersList_1;
        }
        else if(status==2){
            temp = ordersList_2;
        }
        else if(status==3){
            temp = ordersList_3;
        }
        else if(status==4){
            temp = ordersList_4;
        }

        List<OrderService.OrderManager> result=new ArrayList<>();
        for(int i=0; i < temp.size();i++){
            result.add(orderService.convertToOrderManager(temp.get(i)));
        }

        response.setStatus("200");
        response.setResult(result);
        return response;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public Response createOrder(@RequestBody Orders orders,
                                HttpSession session){
        Response response=new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int userId = Integer.parseInt(session.getAttribute("id").toString());
        if(usersMapper.selectByPrimaryKey(userId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        orders.setOrderId(0);
        orders.setStatus(0);
        orders.setDate(new Date());
        orders.setUserId(userId);

        try {
            ordersMapper.insertSelective(orders);
        }catch(Exception e){
            response.setStatus("500");
            response.setError("Inserting Failed");
            return response;
        }

        response.setStatus("201");
        response.setError("Inserting Success!");
        return response;
    }
}