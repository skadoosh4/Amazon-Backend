package com.example.demo.security.controller;

import com.example.demo.security.model.LoginRequest;
import com.example.demo.security.model.LoginResponse;
import com.example.demo.security.service.Login;
import com.example.demo.security.service.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final Login login;
    private final NewUser newUser;

    @Autowired
    public LoginController(Login login, NewUser newUser){
        this.login = login;
        this.newUser = newUser;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return login.execute(request);
    }

    @PostMapping("/createNewUser")
    public ResponseEntity<LoginResponse> createNewUser(@RequestBody LoginRequest request){
        return newUser.execute(request);
    }
}
