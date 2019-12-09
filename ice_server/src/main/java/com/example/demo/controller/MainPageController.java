package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.GameService;
import com.example.demo.service.GameService.GameInfo;
import com.example.demo.service.PublisherService;
import com.example.demo.service.SessionService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainPageController {
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private ConsolesMapper consolesMapper;
    @Autowired
    private PublishersMapper publishersMapper;
    @Autowired
    private GamesMapper gamesMapper;
    @Autowired
    private HasTagMapper hasTagMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ChartMapper chartMapper;
    @Autowired
    private BelongMapper belongMapper;
    @Autowired
    private PlayedOnMapper playedOnMapper;
    @Autowired
    private SaleGameMapper saleGameMapper;
    @Autowired
    private GameService gameService;
    @Autowired
    private static int AskTimes = 0;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private static List<Games> gamesList = new ArrayList<Games>();



    //Header Part
    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public Response getCategories(){
        Response response = new Response();
        List<Categories> AllCate = categoriesMapper.selectAll();
        response.setResult(AllCate);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/getConsoles", method = RequestMethod.GET)
    public Response getConsoles(){
        Response response = new Response();
        List<Consoles> AllConsoles = consolesMapper.selectAll();
        response.setResult(AllConsoles);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/getPublishers", method = RequestMethod.GET)
    public Response getPublishers(){
        Response response = new Response();
        List<Publishers> AllPubs = publishersMapper.selectAll();
        response.setResult(AllPubs);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/getOrderNum", method = RequestMethod.GET)
    public Response getOrderNum(HttpSession session){
        Response response = new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int thisUserId = Integer.parseInt(session.getAttribute("id").toString());
        Integer num = ordersMapper.orderNumOf(thisUserId);
        List<Integer> result = new ArrayList<Integer>();
        result.add(num);
        response.setResult(result);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/getChartNum", method = RequestMethod.GET)
    public Response getChartNum(HttpSession session){
        Response response = new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int thisUserId = Integer.parseInt(session.getAttribute("id").toString());
        Integer num = chartMapper.chartNumOf(thisUserId);
        List<Integer> result = new ArrayList<Integer>();
        result.add(num);
        response.setResult(result);

        response.setStatus("200");
        return response;
    }


    //Game Part
    @RequestMapping(value = "/mainGameNum", method = RequestMethod.GET)
    public Response mainGameNum(HttpSession session){
        Response response = new Response();

        if(sessionService.auth(session).getStatus()!="200") {
            return sessionService.auth(session);
        }
        int thisUserId = Integer.parseInt(session.getAttribute("id").toString());

        List<Integer> result=new ArrayList<>();
        result.add(gamesList.size());
        response.setResult(result);
        response.setStatus("200");
        return  response;
    }

    @RequestMapping(value = "/getGames", method = RequestMethod.GET)
    public Response getGames(@RequestParam(value = "reset") boolean reset){

        Response response = new Response();
        if(reset==true){
            AskTimes=0;
            gamesList = gamesMapper.getAll();
        }

        List<GameInfo> result = new ArrayList<GameInfo>();
        for(int i=AskTimes*12; i<(AskTimes+1)*12; i=i+1){
            if(i>gamesList.size()-1){
                break;
            }
            Games tempGame = gamesList.get(i);
            GameInfo temp = gameService.convertToInfo(tempGame);

            result.add(temp);
        }

        response.setResult(result);
        AskTimes += 1;

        response.setStatus("200");
        return response;
    }

    //flag: 1 add date; 2 price high to low; 3 price low to high; 4 rate high to low; 5 rate low to high
    @RequestMapping(value = "/sortGames", method = RequestMethod.GET)
    public Response sortGames(@RequestParam(value = "flag") int flag){
        AskTimes=0;
        Response response = new Response();
        if(flag==1){
            response = getGames(false);
            return response;
        }

        gamesList = gameService.sort(gamesList, flag);
        List<GameInfo> result = new ArrayList<GameInfo>();
        for(int i=AskTimes*12; i<(AskTimes+1)*12; i=i+1){
            if(i>gamesList.size()-1){
                break;
            }
            Games tempGame = gamesList.get(i);
            GameInfo temp = gameService.convertToInfo(tempGame);

            result.add(temp);
        }

        response.setResult(result);
        AskTimes += 1;

        response.setStatus("200");
        return response;
    }


    public Response resetGamesByTitle(String keyWords){

        Response response = new Response();
        AskTimes = 0;
        gamesList = gamesMapper.searchByTitle(keyWords);

        List<GameInfo> result = new ArrayList<GameInfo>();
        for(int i=AskTimes*12; i<(AskTimes+1)*12; i+=1){
            if(i>gamesList.size()-1){
                break;
            }
            Games tempGame = gamesList.get(i);
            GameInfo temp = gameService.convertToInfo(tempGame);

            result.add(temp);
        }
        AskTimes+=1;

        response.setResult(result);
        response.setStatus("200");
        return response;
    }

    public Response resetGamesByCate(Integer cateId){
        Response response=new Response();
        AskTimes=0;
        List<Belong> belongList=belongMapper.selectByCateId(cateId);
        gamesList.clear();
        for(int i=0;i<belongList.size();i+=1){
            Games temp = gamesMapper.selectByPrimaryKey(belongList.get(i).getGameId());
            gamesList.add(temp);
        }

        List<GameInfo> result = new ArrayList<GameInfo>();
        for(int i=AskTimes*12; i<(AskTimes+1)*12; i+=1){
            if(i>gamesList.size()-1){
                break;
            }
            Games tempGame = gamesList.get(i);
            GameInfo temp = gameService.convertToInfo(tempGame);

            result.add(temp);
        }
        response.setResult(result);
        AskTimes+=1;

        response.setStatus("200");
        return response;
    }

    public Response resetGamesByConsole(Integer consoleId){
        Response response=new Response();
        AskTimes=0;
        List<PlayedOn> playedOns=playedOnMapper.selectByConsoleId(consoleId);
        gamesList.clear();
        for(int i=0;i<playedOns.size();i+=1){
            Games temp = gamesMapper.selectByPrimaryKey(playedOns.get(i).getGameId());
            gamesList.add(temp);
        }

        List<GameInfo> result = new ArrayList<GameInfo>();
        for(int i=AskTimes*12; i<(AskTimes+1)*12; i+=1){
            if(i>gamesList.size()-1){
                break;
            }
            Games tempGame = gamesList.get(i);
            GameInfo temp = gameService.convertToInfo(tempGame);

            result.add(temp);
        }
        response.setResult(result);
        AskTimes+=1;

        response.setStatus("200");
        return response;
    }

    public Response resetGamesByPublisher(Integer publisherId){
        Response response=new Response();
        AskTimes=0;
        List<SaleGame> saleGameList=saleGameMapper.selectByPublisherId(publisherId);
        gamesList.clear();
        for(int i=0;i<saleGameList.size();i+=1){
            Games temp = gamesMapper.selectByPrimaryKey(saleGameList.get(i).getGameId());
            gamesList.add(temp);
        }

        List<GameInfo> result = new ArrayList<GameInfo>();
        for(int i=AskTimes*12; i<(AskTimes+1)*12; i+=1){
            if(i>gamesList.size()-1){
                break;
            }
            Games tempGame = gamesList.get(i);
            GameInfo temp = gameService.convertToInfo(tempGame);

            result.add(temp);
        }
        response.setResult(result);
        AskTimes+=1;

        response.setStatus("200");
        return response;
    }

    //Search Part
    @RequestMapping(value = "/searchGamesByTitle", method = RequestMethod.GET)
    public Response searchGamesByTitle(@RequestParam(value = "keyWords") String keyWords,
                                       @RequestParam(value = "reset") boolean reset){
        if(reset){
            return resetGamesByTitle(keyWords);
        }

        AskTimes=0;
        List<Games> tempGamesList=new ArrayList<>();
        for(int i=0;i<gamesList.size();i+=1){
            if(gamesList.get(i).getTitle().toLowerCase().contains(keyWords.toLowerCase())){
                tempGamesList.add(gamesList.get(i));
            }
        }
        gamesList=tempGamesList;

        return getGames(false);
    }

    @RequestMapping(value = "/searchGamesByCate", method = RequestMethod.GET)
    public Response searchGamesByCate(@RequestParam(value = "cateId") Integer cateId,
                                       @RequestParam(value = "reset") boolean reset){
        if(reset){
            return resetGamesByCate(cateId);
        }

        AskTimes=0;
        List<Games> tempGamesList=new ArrayList<>();
        for(int i=0;i<gamesList.size();i+=1){
            Belong belong = belongMapper.selectByPrimaryKey(gamesList.get(i).getGameId());
            if(belong.getCateId()==cateId){
                tempGamesList.add(gamesList.get(i));
            }
        }
        gamesList=tempGamesList;

        return getGames(false);
    }

    @RequestMapping(value = "/searchGamesByConsole", method = RequestMethod.GET)
    public Response searchGamesByConsole(@RequestParam(value = "consoleId") Integer consoleId,
                                      @RequestParam(value = "reset") boolean reset){
        if(reset){
            return resetGamesByConsole(consoleId);
        }

        AskTimes=0;
        List<Games> tempGamesList=new ArrayList<>();
        for(int i=0;i<gamesList.size();i+=1){
            if(playedOnMapper.selectByPrimaryKey(gamesList.get(i).getGameId(),consoleId)!=null){
                tempGamesList.add(gamesList.get(i));
            }
        }
        gamesList=tempGamesList;

        return getGames(false);
    }

    @RequestMapping(value = "/searchGamesByPublisher", method = RequestMethod.GET)
    public Response searchGamesByPublisher(@RequestParam(value = "publisherId") Integer publisherId,
                                         @RequestParam(value = "reset") boolean reset){
        if(reset){
            return resetGamesByPublisher(publisherId);
        }

        AskTimes=0;
        List<Games> tempGamesList=new ArrayList<>();
        for(int i=0;i<gamesList.size();i+=1){
            if(saleGameMapper.selectByPrimaryKey(publisherId, gamesList.get(i).getGameId())!=null){
                tempGamesList.add(gamesList.get(i));
            }
        }
        gamesList=tempGamesList;

        return getGames(false);
    }

    //Publisher information
    @RequestMapping(value = "/getPublisherInfo", method = RequestMethod.GET)
    public Response getPublisherInfo(@RequestParam(value = "publisherId") Integer publisherId){
        Response response=new Response();
        Publishers publisher=publishersMapper.selectByPrimaryKey(publisherId);
        List<PublisherService.PublishersInfo> result=new ArrayList<>();
        result.add(publisherService.convertToPublisherInfo(publisher));
        response.setResult(result);
        response.setStatus("200");
        return response;
    }
}