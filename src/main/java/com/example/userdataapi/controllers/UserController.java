package com.example.userdataapi.controllers;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private IUserService service;


    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody Users users){
        service.add(users);

        return new ResponseEntity<>(Map.of("message", "User is added!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id){
        service.deleteById(id);

        return new ResponseEntity<>(Map.of("message", "User is deleted!"), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadUsersFromFile(@RequestParam("file") MultipartFile fileToUpload){

        service.uploadUsersFromFile(fileToUpload);


        return new ResponseEntity<>(Map.of("message", "Users is uploaded from file!"), HttpStatus.CREATED);
    }

}
