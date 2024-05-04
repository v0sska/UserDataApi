package com.example.userdataapi.parsers;

import com.example.userdataapi.interfaces.IBirthDateValidator;
import com.example.userdataapi.interfaces.IUserDataParser;
import com.example.userdataapi.pojos.UsersPojo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component("jsonParser")
@AllArgsConstructor
public class JsonUserDataParser implements IUserDataParser {

    private IBirthDateValidator birthDateValidator;

    @Override
    public List<UsersPojo> parseUserDataFromFile(MultipartFile fileToUpload) {
        return null;
    }
}
