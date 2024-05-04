package com.example.userdataapi.components;

import com.example.userdataapi.interfaces.IBirthDateValidator;

import java.time.LocalDate;

public class BirthDateValidator implements IBirthDateValidator {

    private LocalDate currentTime = LocalDate.now();

    @Override
    public boolean validateUserBirthDate(LocalDate birthDateToCheck) {

        if(birthDateToCheck == currentTime)
            throw new IllegalArgumentException("Birth date must be a earlier then current time");
        else {

            int validBirthDate = currentTime.getYear() - birthDateToCheck.getYear();

            if(validBirthDate > 18)
                return true;
            else
                return false;
        }
    }

}
