package com.asynch.completableFuture.restproject.service;

import com.asynch.completableFuture.restproject.entity.User;
import com.asynch.completableFuture.restproject.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    Object target;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception {
         long startTime = System.currentTimeMillis();
         List<User> users = parseCSVFile(file);
         logger.info("Saving the list of user of size", users.size(),
                "" + Thread.currentThread().getName());
          users = userRepository.saveAll(users);
          long endTime = System.currentTimeMillis();
          logger.info("Total time ",(endTime-startTime));

          return CompletableFuture.completedFuture(users);
    }

    //fetch list of users from db
    @Async
    public CompletableFuture<List<User>> findAllUser(){
        logger.info("Get list of user by ", Thread.currentThread().getName());
        List<User> users = userRepository.findAll();

        return CompletableFuture.completedFuture(users);
    }

    //reading data from csv
    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        //try with resource
        try(final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            //reading data until it gets null
            while ((line = bufferedReader.readLine()) != null){
                final String[] data =line.split(",");
                final User user = new User(); //created user and set his info
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                users.add(user); //user added to the list
            }

          return  users;

        } catch (IOException e) {
            logger.error("Failed to parse CSV file ", e);
            throw new Exception("Failed to parse CSV file ", e);
        }
    }

}
