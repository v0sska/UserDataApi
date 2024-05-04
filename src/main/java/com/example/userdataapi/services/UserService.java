package com.example.userdataapi.services;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IBirthDateValidator;
import com.example.userdataapi.interfaces.IUserService;
import com.example.userdataapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UsersRepository repository;

    private IBirthDateValidator birthDateValidator;

    @Override
    public void add(Users users) {

        if(birthDateValidator.validateUserBirthDate(users.getBirthDate()))
            repository.save(users);
        else
            throw new IllegalArgumentException("User must be older than 18 years");
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
