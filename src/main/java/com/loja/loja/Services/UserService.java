package com.loja.loja.Services;

import com.loja.loja.DTOs.UsersDTO;
import com.loja.loja.DTOs.UserRegisterDTO;
import com.loja.loja.Models.User;
import com.loja.loja.Repositories.UserRepository;
import com.loja.loja.Utils.DateUtilities;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    ModelMapper modelMapper;
    public ResponseEntity<User> newRegisteredUser(String firstName,
                                                  String lastName,
                                                  String cpf,
                                                  String state,
                                                  String city,
                                                  String email,
                                                  String password,
                                                  Date dateOfBirth) {
        UserRegisterDTO dto = new UserRegisterDTO();

        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setCpf(cpf);
        dto.setState(state);
        dto.setCity(city);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setDateOfBirth(dateOfBirth);

        User user = modelMapper.map(dto, User.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
    }

    public List<UsersDTO> findUser(){
        var result = repository.getListOfUsers();

        return result.stream().map(UsersDTO::new)
                .toList();
    }

    public UsersDTO userFoundById(Long id){
        var result = repository.getUserById(id);
        UsersDTO dto = new UsersDTO();

        dto.setFirstName(result.getFirstName());
        dto.setLastName(result.getLastName());
        dto.setCpf(result.getCpf());
        dto.setEmail(result.getEmail());
        dto.setDateOfBirth(DateUtilities.DateFormater(result.getDateOfBirth()));

        return dto;
    }

    public ResponseEntity<Object> editUsers(String firstName,
                                          String lastName,
                                          String cpf,
                                          String state,
                                          String city,
                                          String email,
                                          String password,
                                          Date dateOfBirth,
                                          Long id) {
        Optional<User> objectUser = repository.findById(id);
        UserRegisterDTO dto = new UserRegisterDTO();

        if(objectUser.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setCpf(cpf);
        dto.setState(state);
        dto.setCity(city);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setDateOfBirth(dateOfBirth);

        User userModel = objectUser.get();
        modelMapper.map(dto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(userModel));
    }

    public ResponseEntity<Object> deletUsers(Long id) {
        Optional<User> objectUser = repository.findById(id);

        if(objectUser.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        repository.delete(objectUser.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso");
    }
}
