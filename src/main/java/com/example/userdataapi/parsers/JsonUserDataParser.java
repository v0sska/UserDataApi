package com.example.userdataapi.parsers;

import com.example.userdataapi.interfaces.IBirthDateValidator;
import com.example.userdataapi.interfaces.IUserDataParser;
import com.example.userdataapi.pojos.UsersPojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            UsersPojo[] readPojo = objectMapper.readValue(fileToUpload.getInputStream(), UsersPojo[].class);

            for(UsersPojo usersPojo : readPojo){
                if(birthDateValidator.validateUserBirthDate(usersPojo.getBirthDate()))
                    uploadedPojo.add(usersPojo);
                else
                    throw new IllegalArgumentException("User must be older 18!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadedPojo;

    }
}
