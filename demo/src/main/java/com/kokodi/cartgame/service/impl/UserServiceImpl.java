package com.kokodi.cartgame.service.impl;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.service.UserService;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl extends UserService {

    @Override
    @Transactional
    public AuthenticationResponse save(RegisterRequest request) throws RegisterUserException {
        log.info("+ save in UserServiceImpl: login={}, password={}", request.getLogin(), request.getPassword());
        UUID userId = keycloakService.registerUser(request);
        try {
            User user = setUserFields(request);
            user.setUserId(userId);
            repository.save(user);
            AuthenticationResponse authenticate = keycloakService.authenticate(request.getEmail(), request.getPassword());
            log.debug("- save in UserServiceImpl: {}", authenticate);
            return authenticate;
        } catch (IllegalArgumentException | NotUniqueException | ParseException ex) {
            log.error(ex.getMessage());
            keycloakService.deleteUser(userId);
            throw new RegisterUserException(ex.getMessage());
        }
    }

    private User setUserFields(RegisterRequest request) throws NotUniqueException {
        User user = new Users();
        user.setLogin(request.getLogin());
        user.setUserName(getUserName);
        user.setPassword(request.getPassword());
        checkOnUnique(request.getLogin());
        checkOnUniqueRegisterLogin(request.getLogin());
        return user;
    }

    private void checkOnUnique(String login) throws NotUniqueException {
        if (repository.existsByLogin(login))
            throw new NotUniqueException("Такой номер телефона уже зарегистрирован.");
    }
}
