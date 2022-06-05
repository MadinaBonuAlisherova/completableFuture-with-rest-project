package com.asynch.completableFuture.restproject.controller;


import com.asynch.completableFuture.restproject.entity.User;
import com.asynch.completableFuture.restproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {

        for (MultipartFile file : files){
            userService.saveUsers(file);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers(){
       return userService.findAllUser().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/usersByThread", produces = "application/json")
    public ResponseEntity getUsers(){
         CompletableFuture< List< User > > users1 = userService.findAllUser();
        CompletableFuture< List< User > > users2 = userService.findAllUser();
        CompletableFuture< List< User > > users3 = userService.findAllUser();
        CompletableFuture.allOf(users1,users2,users3).join();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
