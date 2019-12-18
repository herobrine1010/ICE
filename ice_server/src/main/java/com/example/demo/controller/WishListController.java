package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.Response;
import com.example.demo.entity.Wishlist;
import com.example.demo.service.GameService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class WishListController {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private WishlistMapper wishlistMapper;
    @Autowired
    private GameService gameService;
    @Autowired
    private GamesMapper gamesMapper;

    //Wishlistはネーミングルールを違反したから、WishListになられたww
    @RequestMapping(value="/checkInMyWishList",method=RequestMethod.GET)
    public Response checkInMyWishList(@RequestParam("gameId") int gameId,HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try {
            Wishlist w = wishlistMapper.selectByPrimaryKey(gameId, thisUserId);
            if (w != null) {
                response.setStatus("200");
                response.setError("Already in your wishlist!");
            } else {
                response.setError("Not yet in your wishlist!");
                response.setStatus("404");
            }
        }catch (Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    @RequestMapping(value="/insertIntoWishList",method=RequestMethod.GET)
    public Response insertIntoWishList(@RequestParam("gameId")int gameId,
            @RequestParam(value="notification",required = false,defaultValue = "1") int notification, HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            Wishlist record=new Wishlist();
            record.setGameId(gameId);
            record.setUserId(thisUserId);
            record.setNotification(notification==1?Boolean.TRUE:Boolean.FALSE);
            wishlistMapper.insert(record);
            response.setStatus("200");
            response.setError("Game has been successfully added to your wishlist!");
        }catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    @RequestMapping(value="/removeFromWishList",method=RequestMethod.GET)
    public Response removeFromWishList(@RequestParam("gameId") int gameId,HttpSession session){
        Response response = new Response();
        //System.out.println(session.getAttribute("id"));
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            wishlistMapper.deleteByPrimaryKey(gameId,thisUserId);
            response.setStatus("200");
            response.setError("Game removed successfully!");
        }catch (Exception e){
            response.setStatus("403");
            response.setError("SQL Error!");
        }
        return response;
    }

    //複数のパラメータがあるんで、リターン値にクラスWishlistを使った。
    @RequestMapping(value="/getMyWishList",method=RequestMethod.POST)
    public Response<Wishlist> getMyWishList(HttpSession session){
        Response response= new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());
        try{
            List<Wishlist> l = wishlistMapper.selectByUserId(thisUserId);
            List<GameService.GameInfo> result=new ArrayList<>();
            if (l.size()==0){
                response.setStatus("404");
                response.setError("Your wishlist is now empty!");
                return response;
            }
            for (int i = 0; i < l.size(); i++) {
                GameService.GameInfo temp_info = gameService.convertToInfo(gamesMapper.selectByPrimaryKey(l.get(i).getGameId()));
                String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "games" + System.getProperty("file.separator") + l.get(i).getGameId().toString() + System.getProperty("file.separator");
                File file = new File(path);
                File[] tempList = file.listFiles();
                for (int j = 0; j < tempList.length; j++) {
                    if(tempList[j].getName().contains("cover")){
                        temp_info.setCover_path("/images/games/" + l.get(i).getGameId().toString() + "/" + tempList[j].getName());
                    }
                }
                result.add(temp_info);
            }
            response.setStatus("200");
            response.setResult(result);

        }catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    //指定のゲームをWishlistに入れたゆーざーの人数を計算すること。
    @RequestMapping(value="/getOthersWishList",method=RequestMethod.GET)
    public Response getOthersWishList(@RequestParam("gameId")int gameId, HttpSession session){
        Response response= new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        try{
            response.setError(String.valueOf(wishlistMapper.selectByGameId(gameId)));
            response.setStatus("200");
        }catch(Exception e){
            response.setError("SQL Error");
            response.setStatus("403");
        }
        return response;
    }

}
//聞け万国の労働者　轟き渡るメーデーの　示威者に起こる足取りと　未来を継ぐる鬨の声
//汝の部署を放棄せよ　汝の価値に目醒むべし　全一日の休業は　社会の虚偽を打つものぞ
//長き搾取に悩みたる　無産の民よ決起せよ　今や廿四時間の　階級戦は来りたり
//立て　労働者　振るい立て　奪い去られし生産を　正義の手もて　取り返せ　彼らのちから何物ぞ
//我らが歩武の先頭に　掲げられたる赤旗を　守れメーデーの労働者　守れメーデーの労働者