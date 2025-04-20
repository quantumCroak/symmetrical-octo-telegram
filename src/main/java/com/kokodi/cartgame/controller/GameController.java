package com.kokodi.cartgame.controller;

import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/game/session")
@Validated
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;



    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    public GameSessionCreateDTO createSession(UUID userId, String userName) {
        return gameService.createGameSession(userId, userName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    public GameSessionGetDTO getSession(UUID sessionId, String username) {
        return gameService.getGameSession(sessionId, username);
    }

    @GetMapping("/{sessionId}/state")
    public ResponseEntity<GameSessionGetDTO> getGameState(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(gameService.getGameStatus(sessionId));
    }
}
