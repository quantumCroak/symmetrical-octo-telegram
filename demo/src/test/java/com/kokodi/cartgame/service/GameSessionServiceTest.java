package com.kokodi.cartgame.service;

import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.repository.GameRepository;
import com.kokodi.cartgame.service.impl.GameServiceImpl;
import com.kokodi.cartgame.util.exception.GameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameSessionServiceTest {
    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    public void setUp() {
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void test_joinGameSession_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String userName = "TestUser";

        when(gameRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () ->
                gameService.joinGameSession(sessionId, userId, userName));

        verify(gameRepository).findById(sessionId);
    }

    @Test
    void test_finishGameSession_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        UserGetDTO userDTO = new UserGetDTO(UUID.randomUUID(), "TestUser");

        when(gameRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () ->
                gameService.finishGameSession(sessionId, userDTO));
    }

    @Test
    void test_getGameStatus_sessionNotFound() {
        UUID sessionId = UUID.randomUUID();

        when(gameRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () ->
                gameService.getGameStatus(sessionId));
    }

    @Test
    public void testJoinGameSession_GameNotFound() {
        UUID sessionId = UUID.randomUUID();
        when(gameRepository.findById(sessionId)).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () -> gameService.joinGameSession(sessionId, UUID.randomUUID(), "user"));
    }

}
