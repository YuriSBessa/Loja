package com.loja.loja.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDTO {
    private String firstName;

    private String lastName;

    private String cpf;

    private String state;

    private String city;

    private String email;

    private String password;

    private Date dateOfBirth;
}
