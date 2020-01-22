package com.example.demo.controller;

import java.io.File;
import java.util.*;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.OrderService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    @Autowired
    private OrdersMapper ordersMapper;


    //gameIdがurlの中に在る原因は、gameIdと評価内容が同時に含まれるクラスはありません。
    //それで、元々にgameIdが商品ページのurlから取られ得るはず。
    @RequestMapping(value = "/addComment",method =RequestMethod.POST)
    public Response addComment(@RequestBody ReviewAdder r, HttpSession session) {
        Response response = new Response();

        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        Reviews review=new Reviews();
        review.setContent(r.getContent());
        review.setReviewDate(new Date());


        //又は、ユーザーは評価内容ただ一つを発表できる。ご注意ください。
        //独立的なAPIは"checkMyComment"のところに見に行ってください。
        try{
            System.out.println(r.getGameId());
            int c=writeReviewMapper.getWhetherCommented(thisUserId,r.getGameId());
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
            hasReview.setGameId(r.getGameId());
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
            response.setError("SQL Error!");
            response.setStatus("403");
            return response;
        }

        Orders order_record = ordersMapper.selectByPrimaryKey(r.getOrderId());
        order_record.setStatus(3);
        ordersMapper.updateByPrimaryKeySelective(order_record);

        response.setError("Comment successful!");
        response.setStatus("200");
        return response;
    }

    @RequestMapping(value="/allComment",method=RequestMethod.GET)
    public Response<ReviewWithUser> allComment
            (@RequestParam("gameId") int gameId,
             @RequestParam(value="from",required = false,defaultValue = "0")int from,
             @RequestParam("to")int to,
             @RequestParam(value="reverse",required = false,defaultValue = "1") int reversed,             //whether the result is arranged by time-reverse order(default) or not
             HttpSession session){
        Response<ReviewWithUser> response = new Response<>();

        System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            List<ReviewsDetailed> commentList=writeReviewMapper.selectAllComment(gameId,from,to-from,reversed);
            if (commentList.isEmpty()){
                response.setError("No comment yet!");
                response.setStatus("404");
            }
            else{
                List<ReviewWithUser> resultList=new ArrayList<>();
                for(int i=0;i<commentList.size();i++){
                    int uid=commentList.get(i).getUserId();
                    Users u=usersMapper.selectByPrimaryKey(uid);
                    ReviewWithUser reviewWithUser=new ReviewWithUser();
                    String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "users" + System.getProperty("file.separator") + u.getUserId().toString() + System.getProperty("file.separator");
                    File file = new File(path);
                    File[] tempList = file.listFiles();
                    if(tempList!=null){
                        reviewWithUser.setAvatarPath("/images/users/"+u.getUserId().toString()+"/" + tempList[0].getName());
                    }
                    reviewWithUser.setUserId(uid);
                    reviewWithUser.setUsername(u.getUserName());
                    reviewWithUser.setContent(commentList.get(i).getContent());
                    reviewWithUser.setReviewDate(commentList.get(i).getReviewDate());
                    resultList.add(reviewWithUser);
                }
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

    @RequestMapping(value="/commentsNumber",method = RequestMethod.GET,params={"gameId"})
    public Response<Integer> commentsNumber(@RequestParam("gameId")int gameId, HttpSession session){
        Response<Integer> response = new Response<>();
        //System.out.println(session.getAttribute("id"));
//        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
//            return sessionService.auth(session);
//        }
        try{
            Integer cCount=writeReviewMapper.commentsCount(gameId);
            List<Integer> l=new ArrayList<>();
            l.add(cCount);
            response.setResult(l);
            response.setStatus("200");
        }catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
            return response;
        }
        return response;
    }

}
