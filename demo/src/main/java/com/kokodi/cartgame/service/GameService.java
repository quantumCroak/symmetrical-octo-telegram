package com.kokodi.cartgame.service;

import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.util.exception.GameNotFoundException;
import com.kokodi.cartgame.util.exception.GameNotInProgressException;
import com.kokodi.cartgame.util.exception.InsufficientPlayersException;
import com.kokodi.cartgame.util.exception.InvalidTargetForAttackException;
import com.kokodi.cartgame.util.exception.InvalidTurnException;
import com.kokodi.cartgame.util.exception.TargetUserRequiredForStealException;
import com.kokodi.cartgame.util.exception.UserNotParticipantException;

import java.util.List;
import java.util.UUID;

public interface GameService {
    GameSessionCreateDTO createGameSession(UUID user, String userName);

    GameSessionGetDTO joinGameSession(UUID sessionId, UUID userId, String userName) throws GameNotFoundException, UserNotParticipantException, InsufficientPlayersException;

    GameSessionGetDTO startGame (UUID sessionId, List<UserGetDTO> users) throws GameNotFoundException;

    GameSessionGetDTO getGameSession(UUID sessionId, String username) throws GameNotFoundException, UserNotParticipantException;

    GameSessionGetDTO makeTurn(UUID sessionId, UUID userId, UUID targetUserId) throws GameNotFoundException, GameNotInProgressException, InvalidTurnException, TargetUserRequiredForStealException, InvalidTargetForAttackException, UserNotParticipantException;

    void finishGameSession(UUID sessionId, UserGetDTO user) throws GameNotFoundException, UserNotParticipantException;

    GameSessionGetDTO getGameStatus(UUID sessionId) throws IllegalStateException, GameNotFoundException;

    GameSession getById(UUID id) throws GameNotFoundException;
}
