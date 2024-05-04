package com.example.userdataapi.interfaces;

import com.example.userdataapi.pojos.UsersPojo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserDataParser {

    List<UsersPojo> parseUserDataFromFile(MultipartFile fileToUpload);

}
