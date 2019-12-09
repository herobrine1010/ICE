package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewWithUser {
    //private Integer reviewId;
    private Integer userId;
    private String username;
    private String avatarPath;
    private String content;
    private Date reviewDate;
}
