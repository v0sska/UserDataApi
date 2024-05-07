package com.example.userdataapi;

import com.example.userdataapi.components.BirthDateValidator;
import com.example.userdataapi.parsers.JsonUserDataParser;
import com.example.userdataapi.pojos.UsersPojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class JsonUserDataParserTest {

    @Mock
    private BirthDateValidator birthDateValidator;

    private JsonUserDataParser jsonUserDataParser;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        jsonUserDataParser = new JsonUserDataParser(birthDateValidator);
    }

    @Test
    public void parseUserDataFromFileTest(){

        String jsonContent = "[{\"firstName\":\"John\"," +
                "\"lastName\":\"Doe\"," +
                "\"birthDate\":\"1990-01-01\"," +
                "\"address\":\"New York\"," +
                "\"phoneNumber\":\"1234567890\"," +
                "\"email\":\"test123@lcdut.com\"}]";

        MultipartFile file = new MockMultipartFile(
                "file",
                "test.json",
                "application/json",
                jsonContent.getBytes());


        UsersPojo userPojo = new UsersPojo();

        userPojo.setFirstName("John");
        userPojo.setLastName("Doe");
        userPojo.setBirthDate(LocalDate.of(1990, 1, 1));
        userPojo.setAddress("New York");
        userPojo.setPhoneNumber("1234567890");
        userPojo.setEmail("test123@lcdut.com");

        when(birthDateValidator.validateUserBirthDate(userPojo.getBirthDate())).thenReturn(true);

        List<UsersPojo> resultList = jsonUserDataParser.parseUserDataFromFile(file);

        verify(birthDateValidator, times(1)).validateUserBirthDate(any(LocalDate.class));

        UsersPojo resultUserPojo = resultList.get(0);
        Assertions.assertEquals(userPojo.getFirstName(), resultUserPojo.getFirstName());
        Assertions.assertEquals(userPojo.getLastName(), resultUserPojo.getLastName());
        Assertions.assertEquals(userPojo.getBirthDate(), resultUserPojo.getBirthDate());
        Assertions.assertEquals(userPojo.getAddress(), resultUserPojo.getAddress());
        Assertions.assertEquals(userPojo.getPhoneNumber(), resultUserPojo.getPhoneNumber());
        Assertions.assertEquals(userPojo.getEmail(), resultUserPojo.getEmail());

    }
}
