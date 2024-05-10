package com.loja.loja.Repositories;

import com.loja.loja.Models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryImplTest {
    @Autowired
    UserRepositoryImpl userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return a list of users from DB")
    void getListOfUsersCase1(){
        Date dateOfBirth = new Date(2004, 04, 05);

        this.createUser("Yuri", "Bessa", "11111111111", "yuri@teste.com",  dateOfBirth);
        this.createUser("Pedro", "Parker", "22222222222", "pedro@teste.com" ,  dateOfBirth);

        List<User> result = this.userRepository.getListOfUsers();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Should not return a list of users from DB")
    void getListOfUsersCase2(){
        List<User> result = this.userRepository.getListOfUsers();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return an user from DB according to id")
    void getUserByIdCase1(){
        Date dateOfBirth = new Date(2004, 04, 05);
        this.createUser("Yuri", "Bessa", "11111111111", "yuri@teste.com", dateOfBirth);

        User result = this.userRepository.getUserById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should not return an user from DB according to id")
    void getUserByIdCase2(){
        User result = this.userRepository.getUserById(1L);

        assertThat(result).isNull();
    }

    private User createUser(String firstName, String lastName, String cpf, String email, Date dateOfBirth){
        User newUser = new User(firstName, lastName, cpf, email, dateOfBirth);
        entityManager.persist(newUser);
        return newUser;
    }
}