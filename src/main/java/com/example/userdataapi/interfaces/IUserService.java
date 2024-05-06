package com.example.userdataapi.interfaces;

import com.example.userdataapi.entities.Users;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {

    void add(Users users);

    void deleteById(Long id);

    void updateAllUserFieldsById(Long id, Users userToUpdate);

    List<Users> listAllUsers();

    void uploadUsersFromFile(MultipartFile fileToUpload);

    List<Users> listUsersByBirthDateBetween(LocalDate fromBirthDate, LocalDate toBirthDate);

    void updateUserFields(Long id, Users userToUpdate);

}
