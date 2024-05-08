package com.example.userdataapi.controllers;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private IUserService service;


    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody Users users){
        service.addUser(users);

        return new ResponseEntity<>(Map.of("message", "User is added!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id){
        service.deleteUserById(id);

        return new ResponseEntity<>(Map.of("message", "User is deleted!"), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadUsersFromFile(@RequestParam("file") MultipartFile fileToUpload){

        service.uploadUsersFromFile(fileToUpload);


        return new ResponseEntity<>(Map.of("message", "Users is uploaded from file!"), HttpStatus.CREATED);
    }

    @GetMapping("/{from}/{to}")
    public ResponseEntity<Object> listUsersByBirthDateBetween(@PathVariable LocalDate from, @PathVariable LocalDate to){
        return new ResponseEntity<>(Map.of("data", service.listUsersByBirthDateBetween(from, to)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAllUserFields(@PathVariable Long id, @RequestBody Users userToUpdate){
        service.updateAllUserFieldsById(id, userToUpdate);

        return new ResponseEntity<>(Map.of("message", "User is updated!"), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUserFields(@PathVariable Long id, @RequestBody Users userToUpdate){

        service.updateUserFields(id, userToUpdate);

        return new ResponseEntity<>(Map.of("message", "User is updated!"), HttpStatus.OK);
    }
}
