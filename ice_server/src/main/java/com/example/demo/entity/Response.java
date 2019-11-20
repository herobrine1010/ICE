package com.example.demo.entity;

import lombok.*;
import java.util.List;

@Data
@Getter
@Setter
@ToString

public class Response<T> {

    public String status;
    //错误代码
    public String error;
    //文字描述的错误类型

    List<T> result;
    //返回查询结果/操作结果
}