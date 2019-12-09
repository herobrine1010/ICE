package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewsDetailed {
    //private Integer reviewId;
    private Integer userId;
    private Date reviewDate;
    private String content;
}