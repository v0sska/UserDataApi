package com.example.userdataapi.parsers;

import com.example.userdataapi.interfaces.IBirthDateValidator;
import com.example.userdataapi.interfaces.IUserDataParser;
import com.example.userdataapi.pojos.UsersPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("jsonParser")
@AllArgsConstructor
public class JsonUserDataParser implements IUserDataParser {

    private IBirthDateValidator birthDateValidator;

    @Override
    public List<UsersPojo> parseUserDataFromFile(MultipartFile fileToUpload) {

        List<UsersPojo> uploadedPojo = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        try {
            UsersPojo[] readPojo = objectMapper.readValue(fileToUpload.getInputStream(), UsersPojo[].class);

            for(UsersPojo usersPojo : readPojo){
                if(birthDateValidator.validateUserBirthDate(usersPojo.getBirthDate()) && isRequirementFieldAreEmpty(usersPojo)
                        && isValidEmail(usersPojo.getEmail()))
                    uploadedPojo.add(usersPojo);
                else
                   continue;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadedPojo;

    }

    private boolean isRequirementFieldAreEmpty(UsersPojo validPojo){

        if(validPojo.getEmail() != null && validPojo.getFirstName() != null && validPojo.getLastName() != null && validPojo.getBirthDate() != null)
            return true;
        else
            return false;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

}
