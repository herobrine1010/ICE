package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.GameService;
import com.example.demo.service.GameService.GameAdder;
import com.example.demo.service.GameService.GameManager;
import com.example.demo.service.OrderService;
import com.example.demo.service.OrderService.OrderManager;
import com.example.demo.service.PublisherService;
import com.example.demo.service.PublisherService.PublishersInfo;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class PublisherPageController {
    @Autowired
    private PublishersMapper publishersMapper;
    @Autowired
    private SaleGameMapper saleGameMapper;
    @Autowired
    private GamesMapper gamesMapper;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private GameService gameService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private SequencesMapper sequencesMapper;
    @Autowired
    private PlayedOnMapper playedOnMapper;
    @Autowired
    private BelongMapper belongMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private HasTagMapper hasTagMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private static List<Games> allGamesList = new ArrayList<>();
    @Autowired
    private static List<Games> presentGameList = new ArrayList<>();
    @Autowired
    private static List<Orders> allOrderList = new ArrayList<>();
    @Autowired
    private static List<Orders> presentOrderList = new ArrayList<>();


    //Information part
    @RequestMapping(value = "/publisherInfo", method = RequestMethod.GET)
    public Response publisherInfo(HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer id = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(id)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        Publishers publishers = publishersMapper.selectByPrimaryKey(id);
        List<PublishersInfo> result = new ArrayList<>();
        result.add(publisherService.convertToPublisherInfo(publishers));
        response.setResult(result);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/updatePublisherInfo", method = RequestMethod.POST)
    public Response updatePublisherInfo(@RequestBody Publishers publisher, HttpSession session){
        Response response=new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer id = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(id)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        publisher.setPublisherId(id);
        try {
            publishersMapper.updateByPrimaryKeySelective(publisher);
        }catch (Exception e){
            response.setStatus("500");
            response.setError("Update Failed!");
            return response;
        }

        response.setError("Update Success!");
        response.setStatus("200");
        return response;
    }


    @RequestMapping(value = "/getAllTags", method=RequestMethod.GET)
    public Response getAllTags(){
        Response response=new Response();
        List<Tags> result=tagsMapper.selectAll();

        response.setStatus("200");
        response.setResult(result);
        return response;
    }


    //Games List
    @RequestMapping(value = "/initGamesList", method = RequestMethod.GET)
    public Response initGamesList(@RequestParam(value = "pageSize") Integer pageSize,
                                  HttpSession session){
        Response response=new Response();
        allGamesList.clear();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer id = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(id)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<SaleGame> saleGameList=saleGameMapper.selectByPublisherId(id);
        allGamesList.clear();
        for(int i=0;i<saleGameList.size();i+=1){
            Games temp = gamesMapper.selectByPrimaryKey(saleGameList.get(i).getGameId());
            allGamesList.add(temp);
        }
        presentGameList.clear();
        presentGameList.addAll(allGamesList);

        List<GameManager> result = new ArrayList<>();
        for(int i=0;i<pageSize;i+=1){
            if(i>presentGameList.size()-1){
                break;
            }
            result.add(gameService.convertToGameManager(presentGameList.get(i)));
        }
        response.setResult(result);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/gameNumber", method = RequestMethod.GET)
    public Response gameNumber(HttpSession session){
        Response response=new Response();
        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer id = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(id)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<Integer> result=new ArrayList<>();
        result.add(presentGameList.size());
        response.setStatus("200");
        response.setResult(result);
        return response;
    }

    @RequestMapping(value = "/searchPublishedGames", method = RequestMethod.GET)
    public Response searchPublishedGames(@RequestParam(value = "query") String query,
                                         @RequestParam(value = "currentPage") Integer currentPage,
                                         @RequestParam(value = "pageSize") Integer pageSize,
                                         HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer id = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(id)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        if(allGamesList.size()==0){
            return initGamesList(pageSize,session);
        }

        presentGameList.clear();
        for(int i=0;i<allGamesList.size();i+=1){
            if(allGamesList.get(i).getTitle().toLowerCase().contains(query.toLowerCase())){
                presentGameList.add(allGamesList.get(i));
            }
        }

        List<GameManager> result = new ArrayList<>();
        for(int i = currentPage*pageSize;i<(currentPage+1)*pageSize;i+=1){
            if(i>presentGameList.size()-1){
                break;
            }
            result.add(gameService.convertToGameManager(presentGameList.get(i)));
        }
        response.setResult(result);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/jumpToGamePage", method = RequestMethod.GET)
    public Response jumpToGamePage(@RequestParam(value = "targetPage") Integer targetPage,
                                   @RequestParam(value = "pageSize") Integer pageSize,
                                   HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer id = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(id)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<GameManager> result = new ArrayList<>();
        for(int i = targetPage*pageSize;i<(targetPage+1)*pageSize;i+=1){
            if(i>presentGameList.size()-1){
                break;
            }
            result.add(gameService.convertToGameManager(presentGameList.get(i)));
        }
        response.setResult(result);
        if(result.size()==0){
            response.setStatus("404");
            response.setError("Out of boundary!");
        }

        response.setStatus("200");
        return response;
    }


    //Add&Delete Game
    @RequestMapping(value = "/addGame", method = RequestMethod.POST)
    public Response addGame(@RequestBody  GameAdder gameAdder,
                            HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        //unpacking
        Games game=new Games();
        game.setTitle(gameAdder.getTitle());
        game.setPrice(gameAdder.getPrice());
        game.setDiscount(gameAdder.getDiscount());
        game.setReleaseDate(gameAdder.getRelease_date());
        game.setPreOrder(gameAdder.getPreOrder());
        game.setDescription(gameAdder.getDescription());

        Integer cate_id=gameAdder.getCate_id();
        String cover=gameAdder.getCover();
        List<Integer> list_console_id=gameAdder.getList_console_id();
        List<Integer> list_tag_id=gameAdder.getList_tag_id();
        List<String> pictures=gameAdder.getPictures();

        //Basic Info
        game.setGameId(0);
        game.setRateCount(0);
        game.setOnSale(true);
        try {
            gamesMapper.insertSelective(game);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setStatus("500");
            response.setError("Error: Information format of noncompliance with requirements.");
            return response;
        }
        Integer gameId=sequencesMapper.selectByPrimaryKey("seq_game_id").getValue().intValue();
        //Publisher
        SaleGame saleGame=new SaleGame();
        saleGame.setGameId(gameId);
        saleGame.setPublisherId(pubId);
        saleGameMapper.insert(saleGame);
        //Consoles
        for(int i=0;i<list_console_id.size();i++){
            PlayedOn playedOn=new PlayedOn();
            playedOn.setConsoleId(list_console_id.get(i));
            playedOn.setGameId(gameId);
            playedOnMapper.insert(playedOn);
        }
        //Cate
        Belong belong=new Belong();
        belong.setCateId(cate_id);
        belong.setGameId(gameId);
        belongMapper.insert(belong);
        //Tags
        for(int i=0;i<list_tag_id.size();i++){
            HasTag hasTag=new HasTag();
            hasTag.setGameId(gameId);
            hasTag.setTagId(list_tag_id.get(i));
            hasTagMapper.insert(hasTag);
        }
        //Cover And Pics
        imageProcess(cover,pictures,gameId);

        List<GameManager> result=new ArrayList<>();
        Games temp=gamesMapper.selectByPrimaryKey(gameId);
        GameManager gm=gameService.convertToGameManager(temp);
        result.add(gm);
        response.setError("Upload Success!");
        response.setStatus("200");
        response.setResult(result);
        return response;
    }

    // type: cover, detail
    @RequestMapping(value = "/postImg", method = RequestMethod.POST)
    public Response postImg(@RequestParam("img") MultipartFile[]  fileList,
                            @RequestParam("type") String type,
                            @RequestParam("game_id") Integer game_id,
                            HttpSession session ) throws IOException {
        Response response=new Response();

        String dirPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "games" + System.getProperty("file.separator") + game_id.toString() + System.getProperty("file.separator");
        if (type.equals("cover")) {
            for (MultipartFile i : fileList) {
                String fileName = i.getOriginalFilename();
                assert fileName != null;
                String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                String localFileName = "cover" + fileSuffix;
                String filePath = dirPath + localFileName;
                File localFile = new File(filePath);
                File imagePath = new File(dirPath);
                if (!imagePath.exists()) {
                    imagePath.mkdirs();
                }
                i.transferTo(localFile);
            }
        }else if(type.equals("detail")){
            int count = 1;
            for (MultipartFile i : fileList) {
                String fileName = i.getOriginalFilename();
                assert fileName != null;
                String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                String localFileName = "" + count + fileSuffix;
                String filePath = dirPath + localFileName;
                File imagePath = new File(dirPath);
                if (!imagePath.exists()) {
                    imagePath.mkdirs();
                }
                File localFile = new File(filePath);
                i.transferTo(localFile);
                count ++;
            }
        }
        System.out.println(dirPath);
        response.setStatus("200");
        return response;
    }

    public boolean imageProcess(@RequestParam(value = "cover") String cover,
                                @RequestParam(value = "pictures") List<String> pictures,
                                @RequestParam(value = "gameId") Integer gameId){
       return true;
    }

    @RequestMapping(value = "/deleteGame", method = RequestMethod.DELETE)
    public Response deleteGame(@RequestParam(value = "gameId") Integer gameId,
                               HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        if(saleGameMapper.selectByGameId(gameId).get(0).getPublisherId()!=pubId){
            response.setStatus("403");
            response.setError("Not sell by this publisher!");
            return response;
        }

        try {
            gamesMapper.deleteByPrimaryKey(gameId);
        }catch (Exception e){
            response.setError("Deleting Error");
            response.setStatus("500");
            return response;
        }

        response.setError("Deleting Success!");
        response.setStatus("204");
        return response;
    }


    @RequestMapping(value = "/modifyGame", method = RequestMethod.POST)
    public Response modifyGame(@RequestBody GameService.GameModifier gameModifier,
                               HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        if(saleGameMapper.selectByGameId(gameModifier.getGame_id()).get(0).getPublisherId()!=pubId){
            response.setStatus("403");
            response.setError("Not sell by this publisher!");
            return response;
        }

        Games gameRecord=gamesMapper.selectByPrimaryKey(gameModifier.getGame_id());
        gameRecord.setTitle(gameModifier.getTitle());
        gameRecord.setDiscount(gameModifier.getDiscount());
        gameRecord.setPreOrder(gameModifier.getPreOrder());
        gameRecord.setDescription(gameModifier.getDescription());
        gameRecord.setPrice(gameModifier.getPrice());
        gamesMapper.updateByPrimaryKeySelective(gameRecord);

        //console
        if(gameModifier.getList_console_id()==null){
            response.setError("Successfully Modified!");
            response.setStatus("200");
            return response;
        }
        List<Integer> consoleIds=gameModifier.getList_console_id();
        playedOnMapper.deleteByGameId(gameRecord.getGameId());
        for (int i = 0; i < consoleIds.size(); i++) {
            PlayedOn temp=new PlayedOn();
            temp.setGameId(gameRecord.getGameId());
            temp.setConsoleId(consoleIds.get(i));
            playedOnMapper.insert(temp);
        }

        response.setError("Successfully Modified!");
        response.setStatus("200");
        return response;
    }


    //Order
    @RequestMapping(value = "/initOrderList", method = RequestMethod.GET)
    public Response initOrderList(@RequestParam(value = "pageSize") Integer pageSize,
                                  HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        allOrderList.clear();
        allOrderList=ordersMapper.selectByPublisherId(pubId);
        presentOrderList.clear();
        presentOrderList.addAll(allOrderList);

        List<OrderManager> result = new ArrayList<>();
        for(int i=0;i<pageSize;i+=1){
            if(i>presentOrderList.size()-1){
                break;
            }
            result.add(orderService.convertToOrderManager(presentOrderList.get(i)));
        }
        response.setResult(result);

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/orderNumber", method = RequestMethod.GET)
    public Response orderNumber(HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<Integer> result=new ArrayList<>();
        result.add(presentOrderList.size());
        response.setResult(result);
        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/searchOrder", method = RequestMethod.GET)
    public Response searchOrder(@RequestParam(value = "query") String query,
                                @RequestParam(value = "currentPage") Integer currentPage,
                                @RequestParam(value = "pageSize") Integer pageSize,
                                HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        if(allOrderList.size()==0){
            return initOrderList(pageSize, session);
        }

        presentOrderList.clear();
        for(int i=0;i<allOrderList.size();i++){
            Orders temp_order=allOrderList.get(i);
            String temp_title=gamesMapper.selectByPrimaryKey(temp_order.getGameId()).getTitle();
            if(temp_title.toLowerCase().contains(query.toLowerCase())){
                presentOrderList.add(temp_order);
            }
        }

        List<OrderManager> result = new ArrayList<>();
        for(int i=currentPage*pageSize;i<(currentPage+1)*pageSize;i++){
            if(i>presentOrderList.size()-1){
                break;
            }
            result.add(orderService.convertToOrderManager(presentOrderList.get(i)));
        }

        response.setResult(result);
        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/jumpToOrderPage", method = RequestMethod.GET)
    public Response jumpToOrderPage(@RequestParam(value = "targetPage") Integer targetPage,
                                    @RequestParam(value = "pageSize") Integer pageSize,
                                    HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        List<OrderManager> result = new ArrayList<>();
        for(int i = targetPage*pageSize;i<(targetPage+1)*pageSize;i+=1){
            if(i>presentOrderList.size()-1){
                break;
            }
            result.add(orderService.convertToOrderManager(presentOrderList.get(i)));
        }
        response.setResult(result);
        if(result.size()==0){
            response.setStatus("404");
            response.setError("Out of boundary!");
        }

        response.setStatus("200");
        return response;
    }

    @RequestMapping(value = "/alterOrder", method = RequestMethod.PUT)
    public Response alterOrder(@RequestParam(value = "order_id") Integer order_id,
                               @RequestParam(value = "status") Integer status,
                               @RequestParam(value = "price") Float price,
                               @RequestParam(value = "address") String address,
                               @RequestParam(value = "contact_tel") String contact_tel,
                               HttpSession session){
        Response response=new Response();

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }
        Integer pubId = (Integer) session.getAttribute("id");
        if(publishersMapper.selectByPrimaryKey(pubId)==null){
            response.setStatus("403");
            response.setError("");
            return response;
        }

        Orders origin_order=ordersMapper.selectByPrimaryKey(order_id);
        SaleGame saleGame=saleGameMapper.selectByGameId(origin_order.getGameId()).get(0);
        if(saleGame.getPublisherId()!=pubId){
            response.setStatus("403");
            response.setError("Not Authorised!");
            return response;
        }

        Orders orders=new Orders();
        orders.setOrderId(order_id);
        if(status!=null){
            if(status>=0&&status<=4){
                orders.setStatus(status);
            }
        }
        if(price!=null){
            orders.setPrice(price);
        }
        if(address!=null){
            orders.setAddress(address);
        }
        if(contact_tel!=null){
            orders.setContactTel(contact_tel);
        }

        try {
            ordersMapper.updateByPrimaryKeySelective(orders);
        }catch (Exception e){
            response.setStatus("500");
            response.setError("Altering Failed!");
            return response;
        }

        Orders currentOrder=ordersMapper.selectByPrimaryKey(order_id);
        List<OrderManager> result=new ArrayList<>();
        result.add(orderService.convertToOrderManager(currentOrder));
        response.setResult(result);

        response.setStatus("200");
        response.setError("Altering success!");
        return response;
    }

}