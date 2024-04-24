package com.example.userdataapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private Date bithDate;

    private String address;

    private String phoneNumber;

    public Users(String email, String firstName, Date bithDate, String address, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.bithDate = bithDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
