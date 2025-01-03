package com.example.demo.security.service;

import com.example.demo.Command;
import com.example.demo.security.model.CustomUser;
import com.example.demo.security.model.LoginRequest;
import com.example.demo.security.model.LoginResponse;
import com.example.demo.security.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewUser implements Command<LoginRequest , LoginResponse> {

    private final CustomUserRepository customUserRepository;

    private final PasswordEncoder encoder;

    private final Login login;

    @Autowired
    public NewUser(CustomUserRepository customUserRepository , PasswordEncoder encoder , Login login){
        this.customUserRepository = customUserRepository;
        this.encoder = encoder;
        this.login = login;
    }

    @Override
    public ResponseEntity<LoginResponse> execute(LoginRequest request) {
        CustomUser user = new CustomUser();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        customUserRepository.save(user);
        return login.execute(request);
    }
}
