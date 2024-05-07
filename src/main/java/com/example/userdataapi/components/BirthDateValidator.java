package com.example.userdataapi.components;

import com.example.userdataapi.exceptions.UserException;
import com.example.userdataapi.interfaces.IBirthDateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BirthDateValidator implements IBirthDateValidator {

    private LocalDate currentTime = LocalDate.now();

    @Override
    public boolean validateUserBirthDate(LocalDate birthDateToCheck) {

        if(birthDateToCheck == currentTime)
            throw new UserException("Birth date must be a earlier then current time");
        else {

            int validBirthDate = currentTime.getYear() - birthDateToCheck.getYear();

            boolean isValidBirthDate = validBirthDate > 18 ? true : false;

            return isValidBirthDate;

        }
    }
}
