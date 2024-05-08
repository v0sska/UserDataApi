package com.example.userdataapi;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IBirthDateValidator;
import com.example.userdataapi.interfaces.IUserService;
import com.example.userdataapi.parsers.JsonUserDataParser;
import com.example.userdataapi.repositories.UsersRepository;
import com.example.userdataapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private IBirthDateValidator birthDateValidator;

    @Mock
    private JsonUserDataParser jsonUserDataParser;

    @Mock
    private UsersRepository usersRepository;

    private IUserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(usersRepository, birthDateValidator, jsonUserDataParser);
    }

    @Test
    public void addUserTest(){
        Users testUser = new Users();

        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));
        testUser.setAddress("New York");
        testUser.setPhoneNumber("1234567890");
        testUser.setEmail("sdafs@ic.net");

        when(birthDateValidator.validateUserBirthDate(testUser.getBirthDate())).thenReturn(true);

        userService.addUser(testUser);

        verify(birthDateValidator, times(1)).validateUserBirthDate(testUser.getBirthDate());

        verify(usersRepository, times(1)).save(testUser);

    }

    @Test
    public void deleteUserByIdTest(){
        Long id = 1L;

        userService.deleteUserById(id);

        verify(usersRepository, times(1)).deleteById(id);
    }

    @Test
    public void updateAllUserFieldTest(){

        Long id = 2L;

        Users userToUpdate = new Users();

        userToUpdate.setFirstName("UpdatedFirstName");
        userToUpdate.setLastName("UpdatedLastName");
        userToUpdate.setBirthDate(LocalDate.of(1995, 5, 5));
        userToUpdate.setAddress("Updated Address");
        userToUpdate.setPhoneNumber("9876543210");
        userToUpdate.setEmail("updatedemail@example.com");

        when(usersRepository.findById(id)).thenReturn(Optional.of(new Users()));

        when(birthDateValidator.validateUserBirthDate(userToUpdate.getBirthDate())).thenReturn(true);

        userService.updateAllUserFieldsById(id, userToUpdate);

        verify(usersRepository, times(1)).findById(id);
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void uploadUsersFromFileTest(){

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
            jsonContent.getBytes()
        );

        userService.uploadUsersFromFile(file);

        verify(jsonUserDataParser, times(1)).parseUserDataFromFile(file);

        verify(usersRepository, times(1)).saveAll(anyList());

    }

    @Test
    public void listUsersByBirthDateBetweenTest(){

        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(1991, 1, 1);

        userService.listUsersByBirthDateBetween(from, to);

        verify(usersRepository, times(1)).findByBirthDateBetween(from, to);

    }

    @Test
    public void updateUserFieldsTest(){

        Long id = 1L;

        Users userToUpdate = new Users();

        userToUpdate.setFirstName("UpdatedFirstName");
        userToUpdate.setLastName("UpdatedLastName");
        userToUpdate.setBirthDate(LocalDate.of(1995, 5, 5));

        when(usersRepository.findById(id)).thenReturn(Optional.of(new Users()));

        when(birthDateValidator.validateUserBirthDate(userToUpdate.getBirthDate())).thenReturn(true);

        userService.updateUserFields(id, userToUpdate);

        verify(usersRepository, times(1)).findById(id);
        verify(usersRepository, times(1)).save(any(Users.class));

    }
}
