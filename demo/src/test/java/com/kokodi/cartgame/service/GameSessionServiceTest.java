package com.kokodi.cartgame.service;

import com.kokodi.cartgame.mapper.CardsMapper;
import com.kokodi.cartgame.mapper.GameSessionMapper;
import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.repository.GameRepository;
import com.kokodi.cartgame.util.exception.GameNotInProgressException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.kokodi.cartgame.model.enums.StatusSessionGame.WAIT_FOR_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class GameSessionServiceTest {
    @InjectMocks
    private GameService gameService;

    @Mock
    private CardsMapper cardsMapper;

    @Mock
    private GameSessionMapper gameSessionMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, String, Object> hashOperations;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    GameSessionCreateDTO gameSessionCreateDTO;

    @Mock
    GameSession gameSession;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void test_joinGameSession_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String userName = "TestUser";

        when(gameRepository.findById(sessionId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                gameService.joinGameSession(sessionId, userId, userName));
    }

    @Test
    void test_startGame_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        List<UserGetDTO> usersDTO = Arrays.asList(new UserGetDTO(UUID.randomUUID(), "TestUser"));

        when(gameRepository.findById(sessionId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                gameService.startGame(sessionId, usersDTO));
    }

    @Test
    void test_getGameSession_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        String userName = "TestUser";

        when(gameRepository.findById(sessionId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                gameService.getGameSession(sessionId, userName));
    }

    @Test
    void test_makeTurn_sessionNotInProgress() {
        UUID sessionId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();

        GameSession session = new GameSession();
        session.setGameSessionId(sessionId);
        session.setStatusSessionGame(WAIT_FOR_PLAYERS);

        when(gameRepository.findById(sessionId)).thenReturn(Optional.of(session));

        assertThrows(GameNotInProgressException.class, () ->
                gameService.makeTurn(sessionId, userId, targetUserId));
    }

    @Test
    void test_finishGameSession_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        UserGetDTO userDTO = new UserGetDTO(UUID.randomUUID(), "TestUser");

        when(gameRepository.findById(sessionId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                gameService.finishGameSession(sessionId, userDTO));
    }

    @Test
    void test_getGameStatus_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();

        when(gameRepository.findById(sessionId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                gameService.getGameStatus(sessionId));
    }
}
