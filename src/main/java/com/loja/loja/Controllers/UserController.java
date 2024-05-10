package com.loja.loja.Controllers;

import com.loja.loja.DTOs.UsersDTO;
import com.loja.loja.Models.User;
import com.loja.loja.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;
    @PostMapping("/register")
    public ResponseEntity<User> createUser(
            @RequestParam(required = true, value = "firstName", defaultValue = "") String firstName,
            @RequestParam(required = true, value = "lastName", defaultValue = "") String lastName,
            @RequestParam(required = true, value = "cpf", defaultValue = "") String cpf,
            @RequestParam(required = true, value = "state", defaultValue = "") String state,
            @RequestParam(required = true, value = "city", defaultValue = "") String city,
            @RequestParam(required = true, value = "email", defaultValue = "") String email,
            @RequestParam(required = true, value = "password", defaultValue = "") String password,

            @RequestParam(required = true, value = "dateOfBirth", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth
    ){
        return service.newRegisteredUser(firstName, lastName, cpf, state, city, email, password, dateOfBirth);
    }
    @GetMapping("/find-user")
    public ResponseEntity<List<UsersDTO>> findListOfUser(){
        return new ResponseEntity<>(service.findUser(), HttpStatus.OK);
    }
    @GetMapping("/find-user-by-id/{id}")
    public ResponseEntity<UsersDTO> findUserById(
            @PathVariable(required = true, value = "id") Long id
    ){
        return new ResponseEntity<>(service.userFoundById(id), HttpStatus.OK);
    }

    @PutMapping("/edit-user/{id}")
    public ResponseEntity<Object> editUser(
            @RequestParam(required = false, value = "firstName") String firstName,
            @RequestParam(required = false, value = "lastName") String lastName,
            @RequestParam(required = false, value = "cpf") String cpf,
            @RequestParam(required = false, value = "state") String state,
            @RequestParam(required = false, value = "city") String city,
            @RequestParam(required = false, value = "email") String email,
            @RequestParam(required = false, value = "password") String password,

            @RequestParam(required = false, value = "dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
            @PathVariable(required = true, value = "id") Long id
    ){
        return service.editUsers(firstName, lastName, cpf, state, city, email, password, dateOfBirth, id);
    }
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Object> deleteUser(
            @PathVariable(required = true, value = "id") Long id
    ){
        return service.deletUsers(id);
    }
}
