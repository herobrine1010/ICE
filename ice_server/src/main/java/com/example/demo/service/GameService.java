package com.example.demo.service;

import com.example.demo.dao.HasTagMapper;
import com.example.demo.dao.TagsMapper;
import com.example.demo.entity.Games;
import com.example.demo.entity.HasTag;
import com.example.demo.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    HasTagMapper hasTagMapper;
    @Autowired
    TagsMapper tagsMapper;

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


    public class GameInfo{
        String coverPath;
        Float price;
        String title;
        List<String> tagsList;

        public void setCoverPath(String coverPath) {
            this.coverPath = coverPath;
        }
        public void setPrice(Float price) {
            this.price = price;
        }
        public void setTagsList(List<String> tagsList) {
            this.tagsList = tagsList;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public Float getPrice() {
            return price;
        }
        public List<String> getTagsList() {
            return tagsList;
        }
        public String getCoverPath() {
            return coverPath;
        }
        public String getTitle() {
            return title;
        }
    }

    public GameInfo convertToInfo(Games game){
        GameInfo gameInfo = new GameInfo();
        gameInfo.setCoverPath(game.getCoverPath());
        gameInfo.setPrice(game.getPrice());
        gameInfo.setTitle(game.getTitle());

        List<HasTag> HasTags = hasTagMapper.selectByGameId(game.getGameId());
        List<String> tagsList = new ArrayList<String>();
        for(int i=0; i<HasTags.size(); i+=1){
            Tags temp = tagsMapper.selectByPrimaryKey(HasTags.get(i).getTagId());
            tagsList.add(temp.getTagName());
        }

        gameInfo.setTagsList(tagsList);

        return gameInfo;
    }

}