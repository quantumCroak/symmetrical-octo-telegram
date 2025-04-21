package com.kokodi.cartgame.service;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.register.RegisterRequest;
import com.kokodi.cartgame.util.exception.NotUniqueException;
import com.kokodi.cartgame.util.exception.RegisterUserException;

public interface UserService {
    User save(RegisterRequest request) throws RegisterUserException, NotUniqueException;

    User findByLogin(String login);
}
