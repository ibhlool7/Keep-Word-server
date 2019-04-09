package com.ibhlool.keepword.service;


import com.ibhlool.keepword.dao.UserRepo;
import com.ibhlool.keepword.model.Log;
import com.ibhlool.keepword.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<User> signUp(User user) {
        String message = "";
        String status = "";
        HttpStatus HTTPCode = HttpStatus.BAD_REQUEST;

        if (userRepo.findByEmail(user.getEmail()) == null) {
            try {
                user.setPassword(hashing(user.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                message = "Internal Problem , try again later";
                status = "0";
                HTTPCode = HttpStatus.INTERNAL_SERVER_ERROR;
                e.printStackTrace();
            }

            user = userRepo.save(user);
            message = "done";
            status = "1";
            HTTPCode = HttpStatus.OK;

        }else{

            message = "user already exist please login";
            status = "0";
            HTTPCode = HttpStatus.NOT_ACCEPTABLE;

        }
        MultiValueMap<String,String> header = new HttpHeaders();
        header.set("message",message);
        header.set("status",status);


        StringBuffer sb = new StringBuffer();


        return new ResponseEntity<User>(user,header,HTTPCode);
    }

    public ResponseEntity<User> login(User user)  {
        String message = "";
        String status = "";
        HttpStatus HTTPCode = HttpStatus.BAD_REQUEST;

        User temp = userRepo.findByEmail(user.getEmail());
        if (temp != null) {
            try {
                user.setPassword(hashing(user.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                message = "try again later !!!";
                status = "0";
                HTTPCode = HttpStatus.INTERNAL_SERVER_ERROR;
                e.printStackTrace();
            }
            if (temp.getPassword().equals(user.getPassword())) {
                message = "done";
                status = "1";
                HTTPCode = HttpStatus.OK;
            } else {
                message = "incorrect password !!!";
                status = "0";
                HTTPCode = HttpStatus.NOT_ACCEPTABLE;
                temp = null;
            }

        } else {
            message = "user not found , please sign-up";
            status = "0";
            HTTPCode = HttpStatus.NOT_FOUND;
            temp = null;
        }
        MultiValueMap<String,String> header = new HttpHeaders();
        header.set("message",message);
        header.set("status",status);
        return new ResponseEntity<User>(temp,header,HTTPCode);
    }

    public ResponseEntity logger(Log entranceLog) {

        String message = "";
        String status = "";
        HttpStatus HTTPCode = HttpStatus.BAD_REQUEST;
        Log log = new Log();
        if (entranceLog.getId() != null) {
            try {
                File file = new File("log.txt");
                if (!file.exists()) {

                    file.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                String t = "time : " + Calendar.getInstance().getTime() + " id : "+entranceLog.getId() ;
                writer.write(t);
                writer.newLine();
                writer.close();
                message = "done";
                status = "1";
                HTTPCode = HttpStatus.OK;
                log.setId(entranceLog.getId());
            } catch (IOException e) {
                message = "failure on creating log";
                status = "0";
                log = null;
                e.printStackTrace();
            }
        } else {
            message = "id is not valid";
            status = "0";
            log = null;
        }


        MultiValueMap<String, String> multiValueMap = new HttpHeaders();
        multiValueMap.set("message", message);
        multiValueMap.set("status", status);

        return new ResponseEntity(log, multiValueMap, HTTPCode);
    }

    private String hashing(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();

        return myHash;
    }

}
