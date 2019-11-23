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
import java.util.List;
import java.util.Objects;
import com.example.demo.entity.ReviewsDetailed;
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
    @RequestMapping(value = "/addComment",method =RequestMethod.POST)
    public Response addComment(@RequestParam("gameId") int gameId,@RequestBody Reviews review, HttpSession session) {
        Response response = new Response();

        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        //又は、ユーザーは評価内容ただ一つを発表できる。ご注意ください。
        //独立的なAPIは"checkMyComment"のところに見に行ってください。
        try{
            System.out.println(gameId);
            int c=writeReviewMapper.getWhetherCommented(thisUserId,gameId);
            if(c==1){
                response.setStatus("403");
                response.setError("You have already Commented !");
                return response;
            }
            else if(c>1){
                //普段通りこれが有り得ない。
                response.setStatus("403");
                response.setError("Fatal error:"+c+" comments found. Please contact the backend.");
                return response;
            }
        }
        catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
            return response;
        }

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

    @RequestMapping(value="/allComment",method=RequestMethod.GET,params={"gameId"})
    public Response<ReviewsDetailed> allComment
            (@RequestParam("gameId") int gameId,
             @RequestParam(value="count",required = false,defaultValue = "10") int commentCount, //the number of comments obtained each time
             @RequestParam(value="reverse",required = false,defaultValue = "1") int reversed,             //whether the result is arranged by time-reverse order(default) or not
             HttpSession session){
        Response<ReviewsDetailed> response = new Response<>();

        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            List<ReviewsDetailed> resultList=writeReviewMapper.selectAllComment(gameId,commentCount,reversed);
            if (resultList.isEmpty()){
                response.setError("No comment yet!");
                response.setStatus("404");
            }
            else{
                response.setResult(resultList);
                response.setStatus("200");
            }
        }catch(Exception e){
            response.setError("SQL error!");
            response.setStatus("403");
        }
        return response;

    }

    //reserved API. Should not be directly used by frontend.
    @RequestMapping(value="/checkMyComment",method=RequestMethod.GET,params={"gameId"})
    public Response checkMyComment(@RequestParam("gameId") int gameId,HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            System.out.println(gameId);
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
