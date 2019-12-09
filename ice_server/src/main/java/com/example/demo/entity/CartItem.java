package com.example.demo.entity;

import lombok.Data;

@Data
public class CartItem {
    private Integer gameId;
    private String title;
    private Float price;
    private String coverPath;
    private Boolean discount;
    private String consoleName;
    private Integer cateId;
    private String cateName;

}
