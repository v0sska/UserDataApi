package com.example.userdataapi.repositories;

import com.example.userdataapi.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    List<Users> findByBirthDateBetween(LocalDate fromBirthDate, LocalDate toBirthDate);

}
