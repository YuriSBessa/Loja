package com.loja.loja.Repositories;

import com.loja.loja.Models.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> getListOfUsers();
    User getUserById(Long id);
}
