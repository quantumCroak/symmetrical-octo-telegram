package com.kokodi.cartgame.service;

import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;

import java.util.List;
import java.util.UUID;

public interface GameService {
    GameSessionCreateDTO createGameSession(UUID user, String userName);

    GameSessionGetDTO joinGameSession(UUID sessionId, UUID userId, String userName);

    GameSessionGetDTO startGame (UUID sessionId, List<UserGetDTO> users);

    GameSessionGetDTO getGameSession(UUID sessionId, String username);

    GameSessionGetDTO makeTurn(UUID sessionId, UUID userId, UUID targetUserId);

    void finishGameSession(UUID sessionId, UserGetDTO user);

    GameSessionGetDTO getGameStatus(UUID sessionId) throws IllegalStateException;
}
