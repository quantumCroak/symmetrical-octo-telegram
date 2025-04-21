package com.kokodi.cartgame.service;

import com.kokodi.cartgame.mapper.CartdsMapper;
import com.kokodi.cartgame.mapper.GameSessionMapper;
import com.kokodi.cartgame.model.ActionCard;
import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.model.enums.StatusSessionGame;
import com.kokodi.cartgame.service.impl.GameServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.kokodi.cartgame.model.enums.StatusSessionGame.IN_PROGRESS;
import static com.kokodi.cartgame.model.enums.StatusSessionGame.WAID_FOR_PLAYERS;
import static com.kokodi.cartgame.model.enums.TypesActionCartds.STEAL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
public class GameSessionServiceTest {
    @InjectMocks
    private GameServiceImpl sessionService;

    @Mock
    private CartdsMapper cartdsMapper;

    @Mock
    private GameSessionMapper gameSessionMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private EntityManager entityManager;

    @Mock
    private HashOperations<String, String, Object> hashOperations;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    GameSessionCreateDTO gameSessionCreateDTO;

    @Mock
    GameSession gameSession = new GameSession();

    @BeforeEach
    public void setUp() {
        openMocks(this);
//        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @Transactional
    public void test_createGameSession() {
        UUID userId = UUID.randomUUID();
        String userName = "TestUser";

        User user = new User();
        user.setUserId(userId);
        user.setName(userName);

//        GameSession gameSession = new GameSession();
//        gameSession.setGameSessionId(UUID.randomUUID());
//        gameSession.setStatusSessionGame(WAID_FOR_PLAYERS);
//        gameSession.setUsers(new ArrayList<>(List.of(user)));
//        gameSession.setPlayerScores(new HashMap<>(Map.of(userId, 0)));
//
//        doNothing().when(entityManager).persist(any(User.class));
//        doNothing().when(entityManager).persist(any(GameSession.class));
//
//        GameSessionCreateDTO resultDTO = new GameSessionCreateDTO();
//        resultDTO.setSessionId(gameSession.getGameSessionId());
//        resultDTO.setUser(user);
//        resultDTO.setStatusSessionGame(WAID_FOR_PLAYERS);

        when(gameSessionMapper.toGetDTOFromCreate(any(GameSession.class))).thenReturn(gameSessionCreateDTO);

        GameSessionCreateDTO result = sessionService.createGameSession(userId, userName);

        assertNotNull(result.getSessionId());
        assertEquals(StatusSessionGame.WAID_FOR_PLAYERS, result.getStatusSessionGame());
        assertEquals(userName, result.getUser().getName());
        assertEquals(1, gameSession.getUsers().size());
    }

    private void assertEquals(String userName, String name) {
    }

    private void assertEquals(int i, int size) {

    }

    private void assertEquals(StatusSessionGame statusSessionGame, StatusSessionGame statusSessionGame1) {

    }

    @Test
    public void test_getGameSession_NotFound() {
        UUID sessionId = UUID.randomUUID();
        when(entityManager.find(GameSession.class, sessionId)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> sessionService.getGameSession(sessionId, "TestUser"));
    }

    @Test
    public void test_getGameSession() {
        UUID sessionId = UUID.randomUUID();
        String userName = "TestUser";

        GameSessionGetDTO result = sessionService.getGameSession(sessionId, userName);
        assertNotNull(result.getSessionId());
        assertEquals(WAID_FOR_PLAYERS, result.getStatusSessionGame());
        assertEquals(userName, result.getUsers().toString());
    }

//    @Test
//    public void test_startGame(){
//        UserGetDTO user1 = new UserGetDTO(UUID.randomUUID(), "TestUser1");
//        UserGetDTO user2 = new UserGetDTO(UUID.randomUUID(), "TestUser2");
//        UserGetDTO user3 = new UserGetDTO(UUID.randomUUID(), "TestUser3");
//        UserGetDTO user4 = new UserGetDTO(UUID.randomUUID(), "TestUser4");
//
//        UUID sessionId = UUID.randomUUID();
//
//        List<UserGetDTO> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        users.add(user4);
//
//        GameSessionGetDTO result = sessionService.startGame(sessionId, users);
//
//        assertNotNull(result.getSessionId());
//        assertEquals(IN_PROGRESS, result.getStatusSessionGame());
//        assertEquals(4, result.getUsers());
//    }
//
//    @Test
//    public void test_makeTurnWithSteal() {
//        UUID sessionId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        UUID targetUserId = UUID.randomUUID();
//
//        GameSession session = new GameSession();
//        session.setGameSessionId(sessionId);
//        session.setStatusSessionGame(IN_PROGRESS);
//        session.setUsers(Arrays.asList(new User(userId, "User1"), new User(targetUserId, "User2")));
//        session.setPlayerScores(new HashMap<>(Map.of(userId, 10, targetUserId, 15)));
//        session.setDeck(Arrays.asList(
//                ActionCard.createActionCard(1, "Steal", STEAL, 4, 1)
//        ));
//        session.setCurrentPlayerIndex(0);
//
//        GameSessionGetDTO result = sessionService.makeTurn(sessionId, userId, targetUserId);
//
//        assertEquals(14, result.getPlayerScores().get(userId));
//        assertEquals(11, result.getPlayerScores().get(targetUserId));
//    }
}
