package com.example.demo.service;

import com.example.demo.dao.ConsolesMapper;
import com.example.demo.dao.GamesMapper;
import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Consoles;
import com.example.demo.entity.Orders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private ConsolesMapper consolesMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private GamesMapper gamesMapper;

    @Getter
    @Setter
    public class OrderManager{
        private Integer order_id;
        private Date order_date;
        private String address;
        private Consoles console;
        private String contact_tel;
        private Integer status;
        private Float price;

        private Integer user_id;
        private String user_name;

        private Integer game_id;
        private String game_name;
    }

    public OrderManager convertToOrderManager(Orders order){
        OrderManager orderManager=new OrderManager();
        orderManager.setOrder_id(order.getOrderId());
        orderManager.setOrder_date(order.getDate());
        orderManager.setAddress(order.getAddress());
        orderManager.setConsole(consolesMapper.selectByPrimaryKey(order.getConsoleId()));
        orderManager.setContact_tel(order.getContactTel());
        orderManager.setStatus(order.getStatus());
        orderManager.setPrice(order.getPrice());

        orderManager.setUser_id(order.getUserId());
        orderManager.setUser_name(usersMapper.selectByPrimaryKey(order.getUserId()).getUserName());

        orderManager.setGame_id(order.getGameId());
        orderManager.setGame_name(gamesMapper.selectByPrimaryKey(order.getGameId()).getTitle());

        return orderManager;
    }
}