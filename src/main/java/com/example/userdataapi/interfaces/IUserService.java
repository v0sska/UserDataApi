package com.example.userdataapi.interfaces;

import com.example.userdataapi.entities.Users;
import java.util.List;

public interface IUserService {

    void add(Users users);

    void deleteById(Long id);

    void updateById(Users users, Long id);

    List<Users> listAllUsers();

}