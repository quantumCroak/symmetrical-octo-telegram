package com.kokodi.cartgame.service;

import com.nimbusds.openid.connect.sdk.AuthenticationResponse;

public interface UserService {
    public AuthenticationResponse save(RegisterRequest request) throws RegisterUserException;
}
