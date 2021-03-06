package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.Games;
import com.example.demo.entity.RateGame;
import com.example.demo.entity.Response;
import com.example.demo.entity.Reviews;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class RateGameController {
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
    private RateGameMapper rateGameMapper;
    @Autowired
    private GamesMapper gamesMapper;

    @RequestMapping(value = "/submitRate",method =RequestMethod.GET)
    public Response submitRate(@RequestParam("gameId") int gameId, @RequestParam("rate") int rate, HttpSession session) {
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            rateGameMapper.submitRate(thisUserId,gameId,rate);
//            RateGameController rateGameController=new RateGameController();
//            Response r=getRate(gameId,session);
//            float rates= Float.parseFloat(r.getError());
//            Games game=gamesMapper.selectByPrimaryKey(gameId);
            Games game = gamesMapper.selectByPrimaryKey(gameId);
            float rates = (game.getAverageRate()*game.getRateCount()+rate)/(game.getRateCount()+1);
            game.setAverageRate(rates);
            game.setRateCount(game.getRateCount()+1);
            gamesMapper.updateByPrimaryKeySelective(game);
            response.setError("Rating successfully submitted!");
            response.setStatus("200");
        }
        catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    @RequestMapping(value="/getRate",method=RequestMethod.GET)
    public Response getRate(@RequestParam("gameId") int gameId,HttpSession session){
        Response response = new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{

            //だってある状況について，評価してしまったユーザーがあるけど、提出したスコアはすべてゼロだ。
            //double r=rateGameMapper.getAverage(gameId);
            double r = gamesMapper.selectByPrimaryKey(gameId).getAverageRate();
            response.setStatus("200");
            response.setError(String.valueOf(r));


        }
        catch (Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;


    }

    @RequestMapping(value="/getMyRate",method = RequestMethod.GET)
    public Response getMyRate(@RequestParam("gameId") int gameId,HttpSession session){
        Response response = new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            int r=rateGameMapper.myRate(gameId,thisUserId);
            if(r==0){
                response.setError("You have not rated yet!");
                response.setStatus("404");
                //ここ特にご注意！！
                //原因不明けれど、rateない場合にSQL異常が発生する。
            }
            else{
                response.setError(String.valueOf(r));
                response.setStatus("200");
                //換言すれば，もう提出したrateはいつもここに、クエリー成功だ。
            }
        }
        catch (Exception e){
            //だからresponse.statusが403の代わりに404を成れせる。
            response.setError("You have not rated yet!");
            response.setStatus("404");
        }
        return response;



    }



    }
