package com.example.springapi.api.controller;
import com.example.springapi.api.model.User;
import com.example.springapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userservice;

    @Autowired
    public UserController(UserService userservice){
        this.userservice=userservice;
    }

    @GetMapping("/try")
    public String getUser(){

        return "probanod el get";

    }
}
