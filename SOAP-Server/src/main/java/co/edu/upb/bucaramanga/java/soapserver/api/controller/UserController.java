package co.edu.upb.bucaramanga.java.soapserver.api.controller;


import co.edu.upb.bucaramanga.java.soapserver.api.model.User;
import co.edu.upb.bucaramanga.java.soapserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/EnviarUsuario")
    public String postUser(@RequestBody User usuario){

        return usuario.toString();

    }
}
