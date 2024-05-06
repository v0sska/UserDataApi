package com.example.userdataapi.services;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IBirthDateValidator;
import com.example.userdataapi.interfaces.IUserDataParser;
import com.example.userdataapi.interfaces.IUserService;
import com.example.userdataapi.pojos.UsersPojo;
import com.example.userdataapi.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UsersRepository repository;

    private IBirthDateValidator birthDateValidator;

    @Qualifier("jsonParser")
    private IUserDataParser jsonParser;

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
    public void updateAllUserFieldsById(Long id, Users userToUpdate) {
        Users oldUser = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found!"));

        oldUser.setFirstName(userToUpdate.getFirstName());
        oldUser.setLastName(userToUpdate.getLastName());
        oldUser.setBirthDate(userToUpdate.getBirthDate());
        oldUser.setAddress(userToUpdate.getAddress());
        oldUser.setPhoneNumber(userToUpdate.getPhoneNumber());
        oldUser.setEmail(userToUpdate.getEmail());

        repository.save(oldUser);
    }

    
    @Override
    public List<Users> listAllUsers() {
        return (List<Users>) repository.findAll();
    }

    @Override
    public void uploadUsersFromFile(MultipartFile fileToUpload) {

        String fileName = fileToUpload.getOriginalFilename();

        List<UsersPojo> receivedPojos;

        if(fileName.endsWith(".json") || fileName.endsWith(".JSON") && !fileToUpload.isEmpty()){
            receivedPojos = jsonParser.parseUserDataFromFile(fileToUpload);
        }
        else
            throw new IllegalArgumentException("Unsupported format!");

        List<Users> usersToUpload = receivedPojos.stream()
                .map(this::pojoToEntity)
                .collect(Collectors.toList());

        repository.saveAll(usersToUpload);
    }

    @Override
    public List<Users> listUsersByBirthDateBetween(LocalDate fromBirthDate, LocalDate toBirthDate) {

        if(fromBirthDate.isBefore(toBirthDate))
            return repository.findByBirthDateBetween(fromBirthDate, toBirthDate);
        else
            throw new IllegalArgumentException("From date must be before to date");

    }

    @Override
    public void updateUserFields(Long id, Users userToUpdate) {
        Users oldUser = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if(userToUpdate.getFirstName() != null)
            oldUser.setFirstName(userToUpdate.getFirstName());
        if(userToUpdate.getLastName() != null)
            oldUser.setLastName(userToUpdate.getLastName());
        if(userToUpdate.getBirthDate() != null)
            oldUser.setBirthDate(userToUpdate.getBirthDate());
        if(userToUpdate.getAddress() != null)
            oldUser.setAddress(userToUpdate.getAddress());
        if(userToUpdate.getPhoneNumber() != null)
            oldUser.setPhoneNumber(userToUpdate.getPhoneNumber());
        if(userToUpdate.getEmail() != null)
            oldUser.setEmail(userToUpdate.getEmail());

        repository.save(oldUser);
    }


    private Users pojoToEntity(UsersPojo usersPojo){

        Users convertedUser = new Users();

        convertedUser.setEmail(usersPojo.getEmail());
        convertedUser.setFirstName(usersPojo.getFirstName());
        convertedUser.setLastName(usersPojo.getLastName());
        convertedUser.setBirthDate(usersPojo.getBirthDate());
        convertedUser.setAddress(usersPojo.getAddress());
        convertedUser.setPhoneNumber(usersPojo.getPhoneNumber());

        return convertedUser;
    }
}
