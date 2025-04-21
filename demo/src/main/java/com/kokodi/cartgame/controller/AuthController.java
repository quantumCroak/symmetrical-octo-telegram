package com.kokodi.cartgame.controller;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.auth.AuthenticationResponse;
import com.kokodi.cartgame.model.dto.register.RegisterRequest;
import com.kokodi.cartgame.security.JwtUtil;
import com.kokodi.cartgame.service.UserService;
import com.kokodi.cartgame.util.exception.NotUniqueException;
import com.kokodi.cartgame.util.exception.RegisterUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/game/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws RegisterUserException, NotUniqueException {
        User user = userService.save(request);
        String token = JwtUtil.generateToken(user.getLogin());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterRequest request) {
        User user = userService.findByLogin(request.getLogin());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = JwtUtil.generateToken(user.getLogin());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные логин или пароль");
    }
}
