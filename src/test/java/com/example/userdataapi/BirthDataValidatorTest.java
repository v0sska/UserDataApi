package com.example.userdataapi;

import com.example.userdataapi.components.BirthDateValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BirthDataValidatorTest {

    @Mock
    BirthDateValidator birthDateValidatorMock;

    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValidBirthDate() {

        LocalDate localDateTest = LocalDate.of(2000, 1, 1);

        when(birthDateValidatorMock.validateUserBirthDate(localDateTest)).thenReturn(true);

        boolean result = birthDateValidatorMock.validateUserBirthDate(localDateTest);

        verify(birthDateValidatorMock, times(1)).validateUserBirthDate(localDateTest);

    }

}
