package com.example.userdataapi;

import com.example.userdataapi.controllers.UserController;
import com.example.userdataapi.entities.Users;
import com.example.userdataapi.interfaces.IUserService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void addUserTest() throws Exception {
        Users user = new Users();

        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("New York");
        user.setPhoneNumber("1234567890");
        user.setEmail("test123@lcdut.com");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());

        verify(userService, times(1)).addUser(any(Users.class));
    }

    @Test
    public void deleteUserByIdTest() throws Exception {

        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUserById(userId);

    }


    @Test
    public void uploadUsersFromFileTest() throws Exception {
        String jsonContent = "[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"1990-01-01\",\"address\":\"New York\",\"phoneNumber\":\"1234567890\",\"email\":\"test123@lcdut.com\"}]";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.json",
                MediaType.APPLICATION_JSON_VALUE,
                jsonContent.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                        .file(file))
                .andExpect(status().isCreated());

        verify(userService, times(1)).uploadUsersFromFile(any(MultipartFile.class));
    }

    @Test
    public void listUsersByBirthDateBetweenTest() throws Exception {
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(1991, 1, 1);

        mockMvc.perform(get("/users/{from}/{to}", from, to))
                .andExpect(status().isOk());

        verify(userService, times(1)).listUsersByBirthDateBetween(from, to);
    }

    @Test
    public void updateAllUserFieldsTest() throws Exception {
        Users userToUpdate = new Users();

        userToUpdate.setFirstName("UpdatedFirstName");
        userToUpdate.setLastName("UpdatedLastName");
        userToUpdate.setBirthDate(LocalDate.of(1995, 5, 5));
        userToUpdate.setAddress("Updated Address");
        userToUpdate.setPhoneNumber("9876543210");
        userToUpdate.setEmail("updatedemail@example.com");

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToUpdate)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateAllUserFieldsById(eq(1L), any(Users.class));

    }

    @Test
    public void updateUserFieldsTest() throws Exception {
        Users userToUpdate = new Users();

        userToUpdate.setFirstName("UpdatedFirstName");
        userToUpdate.setLastName("UpdatedLastName");
        userToUpdate.setBirthDate(LocalDate.of(1995, 5, 5));

        mockMvc.perform(patch("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToUpdate)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUserFields(eq(1L), any(Users.class));

    }
}


