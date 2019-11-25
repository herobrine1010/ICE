package com.example.demo.service;

import com.example.demo.entity.Response;
import com.example.demo.entity.Users;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {

    public Response auth(HttpSession session){
//        Users users =new Users();
        Response response =new Response();
        if(session.getAttribute("id")==null){
            response.setStatus("401");
            response.setError("Please log in!");
            return response;
        }

        response.setStatus("200");
        return response;
    }
}
