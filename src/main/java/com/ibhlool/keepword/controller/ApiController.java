package com.ibhlool.keepword.controller;

import com.ibhlool.keepword.dao.UserRepo;
import com.ibhlool.keepword.model.Group;
import com.ibhlool.keepword.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
class ApiController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/api/{email}/{pass}")
    public Group saveGroup(@RequestBody Group group, @PathVariable(value = "email") String email,
                           @PathVariable(value = "pass") String password){

        User user = userRepo.findByEmail(email);
        if (user!=null && user.getPassword().equals(password)){
            return group;
        }else{
            return null;
        }

    }
    @PostMapping("/api/{email}/")
    public Group saveGroup(@RequestBody Group group, @PathVariable(value = "email") String email){

        User user = userRepo.findByEmail(email);
        if (user!=null ){
            return group;
        }else{
            return null;
        }

    }
}
