package com.loja.loja.Services;

import com.loja.loja.DTOs.UserRegisterDTO;
import com.loja.loja.Models.User;
import com.loja.loja.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;
    @Autowired
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should register a new user and http response has to be CREATED")
    void newRegisteredUserCase1(){
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setFirstName("João");
        userDTO.setLastName("Pedro");
        userDTO.setCpf("11111111111");
        userDTO.setState("RS");
        userDTO.setCity("Porto Alegre");
        userDTO.setEmail("joao@teste");
        userDTO.setPassword("joao123");
        userDTO.setDateOfBirth(new Date(1987, 07, 12));

        User user = new User();

        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> response = userService.newRegisteredUser(
                "João", "Pedro", "11111111111", "RS", "Porto Alegre", "joao@teste", "joao123", new Date(1987, 07, 12)
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user, response.getBody());
    }

    /*@Test
    @DisplayName("Should not register a new user and response has to be INTERNAL_SERVER_ERROR")
    void newRegisteredUserCase2(){
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setFirstName("João");
        userDTO.setLastName("Pedro");
        userDTO.setCpf("11111111111");
        userDTO.setState("RS");
        userDTO.setCity("Porto Alegre");
        userDTO.setEmail("joao@teste");
        userDTO.setPassword("joao123");
        userDTO.setDateOfBirth(new Date(1987, 07, 12));

        when(modelMapper.map(userDTO, User.class)).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(null);

        ResponseEntity<User> response = userService.newRegisteredUser(
                "João", "Pedro", "11111111111", "RS", "Porto Alegre", "joao@teste", "joao123", new Date(1987, 07, 12)
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }*/
}