package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    //このコントローラーは主にIDを使ってゆーざーインフォを取得するの為にデザインした。

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


    @RequestMapping(value="/getUser",method=RequestMethod.GET)
    public Response<Users> getUser(@RequestParam("userId") int userId,HttpSession session ){

        if(!Objects.equals(sessionService.auth(session).getStatus(), "200")) {
            return sessionService.auth(session);
        }

        Response<Users> response = new Response<>();

        try {
            Users result = usersMapper.selectByPrimaryKey(userId);
            String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "users" + System.getProperty("file.separator") + result.getUserId().toString() + System.getProperty("file.separator");
            try {
                File file = new File(path);
                File[] tempList = file.listFiles();
                result.setAvatarPath("/images/users/" + result.getUserId().toString() + "/" + tempList[0].getName());
            }
            catch (Exception e){ }
            if (result!=null) {
                List<Users> resultList = Arrays.asList(result);
                response.setStatus("200");
                response.setResult(resultList);
                response.setError("Got user info successfully!");
            }
            else{
                response.setStatus("404");
                response.setError("No such user!");
            }
        }
        catch(Exception e){
            response.setError("SQL Error!");
            response.setStatus("403");
        }
        return response;
    }

    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public Response postImg(@RequestParam("img") MultipartFile[]  file,
                            HttpSession session ) throws IOException {
        Response response=new Response();

        int thisUserId=Integer.parseInt(session.getAttribute("id").toString());

        String dirPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "users" + System.getProperty("file.separator") + thisUserId + System.getProperty("file.separator");

        String fileName = file[0].getOriginalFilename();
        assert fileName != null;
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        String localFileName = thisUserId + "_avatar" + fileSuffix;
        String filePath = dirPath + localFileName;
        File localFile = new File(filePath);
        File imagePath = new File(dirPath);
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
        file[0].transferTo(localFile);

        response.setStatus("200");
        return response;
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/images/**").addResourceLocations("file:E:/java project/ICE/ice_server/images/");
        }
    }

}
