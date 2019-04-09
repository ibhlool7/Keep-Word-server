package com.ibhlool.keepword.controller;

import com.ibhlool.keepword.model.Log;
import com.ibhlool.keepword.model.User;
import com.ibhlool.keepword.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.InvalidKeyException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("signup")
    public ResponseEntity<User> signUp(@RequestBody User user){
        return authenticationService.signUp(user);

    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) throws UserPrincipalNotFoundException, InvalidKeyException {
        return authenticationService.login(user);
    }

    @RequestMapping("/**")
    public void hit(){
        System.out.println("hit!!!");
    }

    @PostMapping("enter")
    public ResponseEntity enter(@RequestBody Log log){
        return authenticationService.logger(log);
    }

}
