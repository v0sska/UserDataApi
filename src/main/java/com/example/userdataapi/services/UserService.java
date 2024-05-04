package com.example.userdataapi.services;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IUserService;
import com.example.userdataapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UsersRepository repository;

    @Override
    public void add(Users users) {
        repository.save(users);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateById(Users users, Long id) {

    }

    @Override
    public List<Users> listAllUsers() {
        return (List<Users>) repository.findAll();
    }
}
