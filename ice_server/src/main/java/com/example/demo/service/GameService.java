package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GameService {

    @Autowired
    HasTagMapper hasTagMapper;
    @Autowired
    TagsMapper tagsMapper;
    @Autowired
    CategoriesMapper categoriesMapper;
    @Autowired
    BelongMapper belongMapper;
    @Autowired
    PlayedOnMapper playedOnMapper;
    @Autowired
    ConsolesMapper consolesMapper;

    static String imgPath="E:/Schooling/大三上/J2E软工项目/ICE/images/games/";

    //flag: ; 2 price high to low; 3 price low to high; 4 rate high to low; 5 rate low to high
    public List<Games> sort(List<Games> gamesList, int flag){
        if(flag == 2||flag == 3){
            gamesList = sortByPrice(gamesList, flag);
        }
        else if(flag == 4||flag == 5){
            gamesList = sortByRate(gamesList, flag);
        }
        else{
            return gamesList;
        }

        return gamesList;
    }

    public List<Games> sortByPrice(List<Games> gamesList, int flag){
        boolean swapped = false;

        List<Games> toBeSorted = new ArrayList<>();
        for(int i=0; i<gamesList.size();i+=1){
            if(gamesList.get(i).getPrice()!=null){
                toBeSorted.add(gamesList.get(i));
            }
        }

        while(true){
            swapped = false;
            for(int k = 0; k < toBeSorted.size() - 1; k = k + 1){
                if(toBeSorted.get(k).getPrice() > toBeSorted.get(k+1).getPrice()){
                    Games temp = toBeSorted.get(k);
                    toBeSorted.set(k, toBeSorted.get(k+1));
                    toBeSorted.set(k+1, temp);
                    swapped = true;
                }
            }
            if(swapped == false)
            {
                break;
            }
        }

        if(flag == 2)
        {
            List<Games> temp = new ArrayList<Games>();
            for(int i=toBeSorted.size()-1;i>=0;i=i-1){
                temp.add(toBeSorted.get(i));
            }
            toBeSorted = temp;
        }

        for(int i=0; i<gamesList.size();i+=1){
            if(gamesList.get(i).getPrice()==null){
                toBeSorted.add(gamesList.get(i));
            }
        }

        return toBeSorted;
    }

    public List<Games> sortByRate(List<Games> gamesList, int flag){
        boolean swapped = false;

        List<Games> toBeSorted = new ArrayList<>();
        for(int i=0; i<gamesList.size();i+=1){
            if(gamesList.get(i).getAverageRate()!=null){
                toBeSorted.add(gamesList.get(i));
            }
        }

        while(true){
            swapped = false;
            for(int k = 0; k < toBeSorted.size() - 1; k = k + 1){
                if(toBeSorted.get(k).getAverageRate() > toBeSorted.get(k+1).getAverageRate()){
                    Games temp = toBeSorted.get(k);
                    toBeSorted.set(k, toBeSorted.get(k+1));
                    toBeSorted.set(k+1, temp);
                    swapped = true;
                }
            }
            if(swapped == false)
            {
                break;
            }
        }

        if(flag == 4)
        {
            List<Games> temp = new ArrayList<Games>();
            for(int i=toBeSorted.size()-1;i>=0;i=i-1){
                temp.add(toBeSorted.get(i));
            }
            toBeSorted = temp;
        }

        for(int i=0; i<gamesList.size();i+=1){
            if(gamesList.get(i).getAverageRate()==null){
                toBeSorted.add(gamesList.get(i));
            }
        }

        return toBeSorted;
    }


    //Accessory classes
    @Getter
    @Setter
    public class GameInfo{
        private String cover_path;
        private Float price;
        private String title;
        private List<String> tags_list;
    }

    public GameInfo convertToInfo(Games game){
        GameInfo gameInfo = new GameInfo();
        gameInfo.setCover_path(game.getCoverPath());
        gameInfo.setPrice(game.getPrice());
        gameInfo.setTitle(game.getTitle());

        List<HasTag> HasTags = hasTagMapper.selectByGameId(game.getGameId());
        List<String> tagsList = new ArrayList<String>();
        for(int i=0; i<HasTags.size(); i+=1){
            Tags temp = tagsMapper.selectByPrimaryKey(HasTags.get(i).getTagId());
            tagsList.add(temp.getTagName());
        }

        gameInfo.setTags_list(tagsList);

        return gameInfo;
    }

    @Getter
    @Setter
    public class GameManager{
        private Integer game_id;
        private String title;
        private Float price;
        private boolean discount;
        private float average_rate;
        private Date release_date;
        private boolean pre_order;
        private Integer rate_count;
        private String cover;
        private String description;
        private boolean on_sale;

        private Categories category;

        private List<Tags> tags_list;
        private List<String> pictures;
        private List<Consoles> consoles;
    }

    public GameManager convertToGameManager(Games game){

        GameManager gameManager=new GameManager();
        gameManager.setGame_id(game.getGameId());
        gameManager.setTitle(game.getTitle());
        gameManager.setPrice(game.getPrice());
        gameManager.setDiscount(game.getDiscount());
        if(game.getAverageRate()==null){
            gameManager.setAverage_rate(-1);
        }
        else{
            gameManager.setAverage_rate(game.getAverageRate());
        }
        gameManager.setRelease_date(game.getReleaseDate());
        gameManager.setPre_order(game.getPreOrder());
        gameManager.setRate_count(game.getRateCount());
        gameManager.setCover(game.getCoverPath());
        gameManager.setDescription(game.getDescription());
        gameManager.setOn_sale(game.getOnSale());

        //Cate
        Belong belong = belongMapper.selectByPrimaryKey(game.getGameId());
        Categories categories = categoriesMapper.selectByPrimaryKey(belong.getCateId());
        gameManager.setCategory(categories);

        //Tags
        List<HasTag> HasTags = hasTagMapper.selectByGameId(game.getGameId());
        List<Tags> tagsList = new ArrayList<Tags>();
        for(int i=0; i<HasTags.size(); i+=1){
            Tags temp = tagsMapper.selectByPrimaryKey(HasTags.get(i).getTagId());
            tagsList.add(temp);
        }
        gameManager.setTags_list(tagsList);

        //Pictures
        List<String> picList=new ArrayList<>();
        String path=imgPath+game.getGameId().toString();
        File file=new File(path);
        if(file.isDirectory()){
            String[] filelist = file.list();
            for(int i=0;i<filelist.length;i+=1){
                picList.add(path+"/"+filelist[i]);
            }
        }
        gameManager.setPictures(picList);

        //Consoles
        List<Consoles> consolesList=new ArrayList<>();
        List<PlayedOn> playedOns=playedOnMapper.selectByGameId(game.getGameId());
        for(int i=0;i<playedOns.size();i++){
            consolesList.add(consolesMapper.selectByPrimaryKey(playedOns.get(i).getConsoleId()));
        }
        gameManager.setConsoles(consolesList);

        return gameManager;
    }

    @Getter
    @Setter
    static public class GameAdder{
        private String title;
        private Float price;
        private boolean discount;
        private Date release_date;
        private boolean pre_order;
        private String description;

        private Integer cate_id;
        private String cover;
        private List<Integer> list_console_id;
        private List<Integer> list_tag_id;
        private List<String> pictures;

        public boolean getDiscount(){
            return discount;
        }
        public boolean getPreOrder(){
            return pre_order;
        }
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/images/games/**").addResourceLocations("file:E:/Schooling/大三上/J2E软工项目/ICE/images/games/");
        }
    }
}