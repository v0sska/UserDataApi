package com.example.userdataapi.interfaces;

import com.example.userdataapi.entities.Users;
import java.time.LocalDate;


public interface IBirthDateValidator {

    boolean validateUserBirthDate(LocalDate birthDateToCheck);


}
