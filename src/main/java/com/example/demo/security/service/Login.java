package com.example.demo.security.service;

import com.example.demo.Query;
import com.example.demo.security.JWTUtil;
import com.example.demo.security.model.LoginRequest;
import com.example.demo.security.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Login implements Query<LoginRequest , LoginResponse> {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public Login(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<LoginResponse> execute(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername() , request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JWTUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
