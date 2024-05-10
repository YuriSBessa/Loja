package com.loja.loja.DTOs;

import com.loja.loja.Models.User;
import com.loja.loja.Utils.DateUtilities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersDTO {
    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String dateOfBirth;

    public UsersDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.cpf = user.getCpf();
        this.email = user.getEmail();
        this.dateOfBirth = DateUtilities.DateFormater(user.getDateOfBirth());
    }
}
