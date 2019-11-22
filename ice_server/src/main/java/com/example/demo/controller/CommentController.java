package com.example.demo.controller;


import com.example.demo.dao.HasReviewMapper;
import com.example.demo.dao.ReviewsMapper;
import com.example.demo.dao.UsersMapper;
import com.example.demo.dao.WriteReviewMapper;
import com.example.demo.entity.*;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class CommentController {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private HasReviewMapper hasReviewMapper;
    @Autowired
    private WriteReviewMapper writeReviewMapper;
    @Autowired
    private ReviewsMapper reviewsMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;


    //gameIdがurlの中に在る原因は、gameIdと評価内容が同時に含まれるクラスはありません。
    //それで、元々にgameIdが商品ページのurlから取られ得るはず。
    @RequestMapping(value = "/addComment/gameId={gameId}",method =RequestMethod.POST)
    public Response addComment(@PathVariable("gameId") int gameId,@RequestBody Reviews review, HttpSession session) {
        Response response = new Response();

        System.out.println(session.getAttribute("id"));
        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        //又は、ユーザーは評価内容一つを発表できる。ご注意ください。


        //アップデートさせるべきテーブルは先ずREVIEWS、そしてHAS_REVIEWとWRITE_REVIEWである。
        try {
            reviewsMapper.insert(review);
            //review{review_id,content}
            HasReview hasReview = new HasReview();
            hasReview.setGameId(gameId);
            hasReview.setReviewId(reviewsMapper.getLastInsertReviewId());
            hasReviewMapper.insert(hasReview);
            //has_review{game_id(√),review_id}
            WriteReview writeReview = new WriteReview();
            writeReview.setReviewId(reviewsMapper.getLastInsertReviewId());
            writeReview.setUserId(thisUserId);
            writeReviewMapper.insert(writeReview);
            //write_review{review_id,user_id(√)}
        }
        catch(Exception e){
            response.setError("Comment failed!");
            response.setStatus("403");
            return response;
        }
        response.setError("Comment successful!");
        response.setStatus("200");
        return response;

    }


    @RequestMapping(value="/allComment/gameId={gameId}&count={count}",method=RequestMethod.GET)
    public Response allComment(@PathVariable("gameId") int gameId,@PathVariable("count") int commentCount,HttpSession session){
        Response response = new Response();
        System.out.println(session.getAttribute("id"));
        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        //テスト：
        response.setError("count:"+commentCount+";gameId:"+gameId);
        response.setStatus("200");
        return response;
        //間違いない


    }

    @RequestMapping(value="/checkMyComment/gameId={gameId}",method=RequestMethod.GET)
    public Response checkMyComment(@PathVariable("gameId") int gameId,HttpSession session){
        Response response = new Response();
        System.out.println(session.getAttribute("id"));
        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            int c=writeReviewMapper.getWhetherCommented(thisUserId,gameId);
            response.setStatus("200");
            response.setError(String.valueOf(c));
        }
        catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
            return response;
        }
        return response;

    }
}
