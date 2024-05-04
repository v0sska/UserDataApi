package com.example.userdataapi.controllers;

import com.example.userdataapi.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
public class UserController {

    private IUserService service;

}
