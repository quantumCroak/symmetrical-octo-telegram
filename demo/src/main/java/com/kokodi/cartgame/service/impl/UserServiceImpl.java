package com.kokodi.cartgame.service.impl;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.register.RegisterRequest;
import com.kokodi.cartgame.repository.UserRepository;
import com.kokodi.cartgame.service.UserService;
import com.kokodi.cartgame.util.exception.NotUniqueException;
import com.kokodi.cartgame.util.exception.RegisterUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User save(RegisterRequest request) throws RegisterUserException, NotUniqueException {
        log.info("Registering user with login: {}", request.getLogin());

        if (repository.existsByLogin(request.getLogin())) {
            throw new NotUniqueException("Логин уже зарегистрирован.");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getUserName());

        try {
            return repository.save(user);
        } catch (Exception e) {
            log.error("Ошибка при регистрации: {}", e.getMessage());
            throw new RegisterUserException("Не удалось зарегистрировать пользователя: " + e.getMessage());
        }
    }

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с логином " + login + " не найден"));
    }
}
