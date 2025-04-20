package com.kokodi.cartgame.service;

import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.enums.StatusSessionGame;
import com.kokodi.cartgame.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
public class GameSessionServiceTest {
    @InjectMocks
    GameServiceImpl sessionService;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        UUID sessionId = UUID.randomUUID();
        String userName = "TestUser";

    }

    @Test
    public void test_createGameSession() {
        UUID userId = UUID.randomUUID();
        String userName = "TestUser";

        GameSessionCreateDTO result = sessionService.createGameSession(userId, userName);
        assertNotNull(result.getSessionId());
        assertEquals(StatusSessionGame.WAID_FOR_PLAYERS, result.getStatusSessionGame());
        assertEquals(1, result.getUser());
        assertEquals(userName, result.getUser());
    }

    private void assertEquals(StatusSessionGame statusSessionGame, StatusSessionGame statusSessionGame1) {
    }

    @Test
    public void test_getGameSession() {}



}
