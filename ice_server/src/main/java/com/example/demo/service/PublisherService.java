package com.example.demo.service;

import com.example.demo.dao.PublishersMapper;
import com.example.demo.dao.UsersMapper;
import com.example.demo.entity.Publishers;
import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    @Autowired
    PublishersMapper publishersMapper;

    public Response Exist(String publisherName){
        Response response=new Response();
        if(publishersMapper.selectByName(publisherName).size()==0){
            response.setStatus("404");
            response.setError("Publisher not exist");
            return response;
        }
        response.setStatus("200");
        return response;
    }

    @Getter
    @Setter
    public class PublishersInfo{
        private Integer publisher_id;
        private String publisher_name;
        private String logo_path;
        private String pwd;
        private String description;
    }

    public PublishersInfo convertToPublisherInfo(Publishers publishers){
        PublishersInfo publisherInfo=new PublishersInfo();
        publisherInfo.setPublisher_id(publishers.getPublisherId());
        publisherInfo.setPublisher_name(publishers.getPublisherName());
        publisherInfo.setLogo_path(publishers.getLogoPath());
        publisherInfo.setDescription(publishers.getDescription());
        publisherInfo.setPwd(publishers.getPwd());


        return publisherInfo;
    }
}