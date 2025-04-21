package com.kokodi.cartgame.controller;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.service.GameService;
import com.kokodi.cartgame.service.UserService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game/session")
@Validated
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final UserService userService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public GameSessionCreateDTO createSession() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login);
        return gameService.createGameSession(user.getUserId(), user.getName());
    }

    @GetMapping("/{sessionId}/join")
    @ResponseStatus(HttpStatus.OK)
    public GameSessionGetDTO joinSession(@PathVariable @NotNull UUID sessionId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login);
        return gameService.joinGameSession(sessionId, user.getUserId(), user.getName());
    }

    @PostMapping("/{sessionId}/start")
    @ResponseStatus(HttpStatus.OK)
    public GameSessionGetDTO startGame(@PathVariable @NotNull UUID sessionId,
                                       @RequestBody List<UserGetDTO> users) {
        return gameService.startGame(sessionId, users);
    }

    @GetMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public GameSessionGetDTO getSession(@PathVariable @NotNull UUID sessionId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return gameService.getGameSession(sessionId, login);
    }

    @PostMapping("/{sessionId}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void finishSession(@PathVariable @NotNull UUID sessionId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login);
        gameService.finishGameSession(sessionId, new UserGetDTO(user.getUserId(), user.getName()));
    }

    @GetMapping("/{sessionId}/status")
    public ResponseEntity<GameSessionGetDTO> getGameStatus(@RequestParam UUID sessionId) {
        return ResponseEntity.ok(gameService.getGameStatus(sessionId));
    }
}
