package com.example.demo.entity;

import lombok.Data;

/**
 * @program: demo
 * @description:
 * @author: Xuefei Lv
 * @create: 2019/12/10
 **/
@Data
public class ReviewAdder {
    private Integer orderId;
    private String content;
    private int gameId;
}
