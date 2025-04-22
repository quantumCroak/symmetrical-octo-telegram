package com.kokodi.cartgame.service.impl;

import com.kokodi.cartgame.mapper.CardsMapper;
import com.kokodi.cartgame.mapper.GameSessionMapper;
import com.kokodi.cartgame.model.ActionCard;
import com.kokodi.cartgame.model.Cards;
import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.PointsCard;
import com.kokodi.cartgame.model.Turns;
import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.CardsGetDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.model.enums.StatusSessionGame;
import com.kokodi.cartgame.model.enums.TypesActionCards;
import com.kokodi.cartgame.repository.GameRepository;
import com.kokodi.cartgame.repository.UserRepository;
import com.kokodi.cartgame.service.GameService;
import com.kokodi.cartgame.util.exception.GameNotFoundException;
import com.kokodi.cartgame.util.exception.GameNotInProgressException;
import com.kokodi.cartgame.util.exception.InvalidTargetForAttackException;
import com.kokodi.cartgame.util.exception.InvalidTurnException;
import com.kokodi.cartgame.util.exception.TargetUserRequiredForStealException;
import com.kokodi.cartgame.util.exception.UserNotParticipantException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kokodi.cartgame.model.ActionCard.createActionCard;
import static com.kokodi.cartgame.model.PointsCard.createPointsCard;
import static com.kokodi.cartgame.model.enums.StatusSessionGame.FINISHED;
import static com.kokodi.cartgame.model.enums.TypesActionCards.ATTACK;
import static com.kokodi.cartgame.model.enums.TypesActionCards.BAN;
import static com.kokodi.cartgame.model.enums.TypesActionCards.BLOCK;
import static com.kokodi.cartgame.model.enums.TypesActionCards.DEFENSE;
import static com.kokodi.cartgame.model.enums.TypesActionCards.DOUBLE_DOWN;
import static com.kokodi.cartgame.model.enums.TypesActionCards.EXTRA_TURN;
import static com.kokodi.cartgame.model.enums.TypesActionCards.HEAL;
import static com.kokodi.cartgame.model.enums.TypesActionCards.SHUFFLE;
import static com.kokodi.cartgame.model.enums.TypesActionCards.STEAL;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final CardsMapper cardsMapper;
    private final GameSessionMapper gameSessionMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private static Map<UUID, Boolean> defenseStatus = new HashMap<>();
    private static Map<TypesActionCards, Integer> bannedCards = new HashMap<>();
    private static List<UUID> extraTurns = new ArrayList<>();

    @Transactional
    public GameSessionCreateDTO createGameSession(UUID userId, String userName) {
        try {
            User user = new User();
            user.setUserId(userId);
            user.setName(userName);
            userRepository.save(user);

            GameSession gameSession = new GameSession();
            gameSession.setGameSessionId(UUID.randomUUID());
            gameSession.setStatusSessionGame(StatusSessionGame.WAIT_FOR_PLAYERS);
            gameSession.setUsers(new ArrayList<>(List.of(user)));
            gameSession.setPlayerScores(new HashMap<>());
            gameSession.getPlayerScores().put(userId, 0);
            gameRepository.save(gameSession);

            redisTemplate.opsForHash().put("game:" + gameSession.getGameSessionId(), "player:" + userId, 0);
            redisTemplate.opsForValue().set("game:" + gameSession.getGameSessionId() + ":code:" + userId, UUID.randomUUID().toString());

            return gameSessionMapper.toGetDTOFromCreate(gameSession);
        } catch (RedisConnectionFailureException e) {
            log.error("Ошибка подключения к Redis: {}", e.getMessage());
            throw new RuntimeException("Не удалось сохранить данные сессии в кэш");
        }
    }

    @Override
    @Transactional
    public GameSessionGetDTO joinGameSession(UUID sessionId, UUID userId, String userName) throws GameNotFoundException, UserNotParticipantException {
        GameSession gameSession = gameRepository.findById(sessionId)
                .orElseThrow(() -> new GameNotFoundException("Game session not found"));

        if (gameSession.getStatusSessionGame() != StatusSessionGame.WAIT_FOR_PLAYERS) {
            throw new IllegalStateException("Game session is not accepting new players");
        }

        if (gameSession.getUsers().size() < 2 || gameSession.getUsers().size() > 4) {
            throw new IllegalStateException("Insufficient number of players");
        }

        boolean isAlreadyUsers = gameSession.getUsers().stream()
                .anyMatch(user -> user.getUserId().equals(userId));
        if (isAlreadyUsers) {
            throw new UserNotParticipantException("User is already a participant in this game session");
        }

        User user = userRepository.findById(userId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUserId(userId);
                    newUser.setName(userName);
                    return userRepository.save(newUser);
                });

        gameSession.getUsers().add(user);
        gameRepository.save(gameSession);

        return gameSessionMapper.toGetDTO(gameSession);
    }

    @Override
    @Transactional
    public GameSessionGetDTO getGameSession(UUID sessionId, String username) throws GameNotFoundException, UserNotParticipantException {
        GameSession gameSession = gameRepository.findById(sessionId)
                .orElseThrow(() -> new GameNotFoundException("Game session not found"));

        boolean isParticipant = gameSession.getUsers().stream()
                .anyMatch(user -> user.getName().equals(username));
        if (!isParticipant) {
            throw new UserNotParticipantException("User is not a participant of this game session");
        }

        return gameSessionMapper.toGetDTO(gameSession);
    }

    @Override
    public GameSessionGetDTO startGame(UUID sessionId, List<UserGetDTO> users) throws GameNotFoundException {
        GameSession gameSession = gameRepository.findById(sessionId)
                .orElseThrow(() -> new GameNotFoundException("Game session not found"));

        List<Cards> cartds = new ArrayList<>();
        generateDeck(cartds);
        gameSession.setTurns(new ArrayList<>());
        gameSession.setPlayerScores(new java.util.HashMap<>());
        gameSession.setSkipTurns(0);
        gameSession.setStatusSessionGame(StatusSessionGame.IN_PROGRESS);


        return gameSessionMapper.toGetDTO(gameSession);
    }

    @Override
    public void finishGameSession(UUID sessionId, UserGetDTO user) throws GameNotFoundException, UserNotParticipantException {
        log.info("Finished game session {}", sessionId);
        GameSessionGetDTO gameSession = getGameSession(sessionId, user.getUsername());
        gameSession.setStatusSessionGame(FINISHED);
        gameSession.setDeck(null);
        GameSession entity = gameSessionMapper.toEntity(gameSession);
        gameRepository.save(entity);

        redisTemplate.delete("game:" + sessionId);
        gameSession.getUsers().forEach(u -> redisTemplate.delete("game:" + sessionId + ":code:" + u.getUserId()));
    }

    @Transactional
    @Override
    public GameSessionGetDTO makeTurn(UUID sessionId, UUID userId, UUID targetUserId) throws GameNotFoundException, GameNotInProgressException, InvalidTurnException, TargetUserRequiredForStealException, InvalidTargetForAttackException, UserNotParticipantException {
        GameSession gameSession = gameRepository.findById(sessionId)
                .orElseThrow(() -> new GameNotFoundException("Game session not found"));

        if (gameSession == null || gameSession.getStatusSessionGame() != StatusSessionGame.IN_PROGRESS) {
            throw new GameNotInProgressException("Game is not in progress");
        }

        if (!gameSession.getUsers().get(gameSession.getCurrentPlayerIndex()).getUserId().equals(userId)) {
            throw new InvalidTurnException("Not your turn");
        }

        if (gameSession.getSkipTurns() > 0) {
            gameSession.setSkipTurns(gameSession.getSkipTurns() - 1);
            gameSession.setCurrentPlayerIndex((gameSession.getCurrentPlayerIndex() + 1) % gameSession.getUsers().size());
            gameRepository.save(gameSession);
            redisTemplate.opsForValue().set("game:" + sessionId + ":skipTurns", gameSession.getSkipTurns());
            return gameSessionMapper.toGetDTO(gameSession);
        }

        if (gameSession.getDeck().isEmpty()) {
            gameSession.setStatusSessionGame(StatusSessionGame.FINISHED);
            gameRepository.save(gameSession);
            return gameSessionMapper.toGetDTO(gameSession);
        }

        Cards card = gameSession.getDeck().remove(0);
        Turns turn = new Turns();
        turn.setActiveUserId(userId);
        turn.setGameSession(gameSession);

        boolean shouldEndTurn = false;
        if (card instanceof PointsCard) {
            shouldEndTurn = isPointsCard(gameSession, userId, card, turn);
        } else if (card instanceof ActionCard) {
            shouldEndTurn = isActionCard(gameSession, userId, targetUserId, (ActionCard) card, turn);
        }

        gameSession.getTurns().add(turn);
        if (!shouldEndTurn && !hasExtraTurn(userId)) {
            gameSession.setCurrentPlayerIndex((gameSession.getCurrentPlayerIndex() + 1) % gameSession.getUsers().size());
        }

        decrementBanTurns();

        if (isDefended(userId)) {
            deactivateDefense(userId);
        }

        gameRepository.save(gameSession);

        gameSession.getPlayerScores().forEach((id, score) ->
                redisTemplate.opsForHash().put("game:" + sessionId, "player:" + id, score));
        redisTemplate.opsForValue().set("game:" + sessionId + ":deck", gameSession.getDeck().size());

        return gameSessionMapper.toGetDTO(gameSession);
    }

    private boolean isPointsCard(GameSession gameSession, UUID userId, Cards card, Turns turn) {
        int currentScore = gameSession.getPlayerScores().get(userId);
        int newScore = currentScore + card.getValue();
        if (newScore >= 30) {
            newScore = 30;
            gameSession.setStatusSessionGame(StatusSessionGame.FINISHED);
            log.info("Player {} has won the game!", userId);
        }
        gameSession.getPlayerScores().put(userId, newScore);
        turn.setAction("Gain " + card.getValue() + " points");
        return gameSession.getStatusSessionGame() == StatusSessionGame.FINISHED;
    }

    private boolean isActionCard(GameSession gameSession, UUID userId, UUID targetUserId, ActionCard actionCard, Turns turn) throws InvalidTargetForAttackException, TargetUserRequiredForStealException, UserNotParticipantException {
        TypesActionCards actionType = actionCard.getActionCards().iterator().next();
        if (isCardTypeBanned(actionType)) {
            turn.setAction("Action " + actionType + " is banned");
            return true;
        }

        if (targetUserId != null && gameSession.getUsers().stream().noneMatch(u -> u.getUserId().equals(targetUserId))) {
            throw new UserNotParticipantException("Target user not in game session");
        }

        switch (actionType) {
            case BLOCK:
                gameSession.setSkipTurns(1);
                turn.setAction("Block next turn");
                break;
            case STEAL:
                if (targetUserId == null) {
                    throw new TargetUserRequiredForStealException("Target user required for steal");
                }
                if (isDefended(targetUserId)) {
                    turn.setAction("Steal blocked by defense for user " + targetUserId);
                    break;
                }
                int targetScore = gameSession.getPlayerScores().get(targetUserId);
                int stolen = Math.min(actionCard.getValue(), targetScore);
                gameSession.getPlayerScores().put(targetUserId, targetScore - stolen);
                gameSession.getPlayerScores().put(userId, gameSession.getPlayerScores().get(userId) + stolen);
                turn.setAction("Steal " + stolen + " from user " + targetUserId);
                break;
            case DOUBLE_DOWN:
                int doubledScore = gameSession.getPlayerScores().get(userId) * 2;
                if (doubledScore >= 30) {
                    doubledScore = 30;
                    gameSession.setStatusSessionGame(StatusSessionGame.FINISHED);
                    log.info("Player {} has won the game!", userId);
                }
                gameSession.getPlayerScores().put(userId, doubledScore);
                turn.setAction("Double down");
                break;
            case ATTACK:
                if (targetUserId == null || targetUserId.equals(userId)) {
                    throw new InvalidTargetForAttackException("Invalid target for attack");
                }
                if (isDefended(targetUserId)) {
                    turn.setAction("Attack blocked by defense for user " + targetUserId);
                    break;
                }
                targetScore = gameSession.getPlayerScores().get(targetUserId);
                int attack = Math.min(actionCard.getValue(), targetScore);
                gameSession.getPlayerScores().put(targetUserId, targetScore - attack);
                turn.setAction("Attack user " + targetUserId + " for " + attack + " points");
                break;
            case DEFENSE:
                activateDefense(userId);
                turn.setAction("Activate defense for user " + userId);
                break;

            case HEAL:
                int healAmount = actionCard.getValue();
                int currentScore = gameSession.getPlayerScores().get(userId);
                int newScore = Math.min(currentScore + healAmount, 30);
                gameSession.getPlayerScores().put(userId, newScore);
                turn.setAction("Heal user " + userId + " for " + healAmount + " points");
                break;

            case BAN:
                TypesActionCards bannedType = TypesActionCards.ATTACK;
                int banTurns = actionCard.getValue();
                banCardType(bannedType, banTurns);
                turn.setAction("Ban " + bannedType + " cards for " + banTurns + " turns");
                break;

            case EXTRA_TURN:
                setExtraTurnForPlayer(userId);
                turn.setAction("Grant extra turn to user " + userId);
                break;

            case SHUFFLE:
                for (User player : gameSession.getUsers()) {
                    Collections.shuffle(player.getCards());
                }
                turn.setAction("Shuffle all players' cartds");
                break;
            default:
                throw new UnsupportedOperationException("Action not implemented: " + actionCard.getActionCards());
        }
        return false;
    }

    @Override
    public GameSessionGetDTO getGameStatus(UUID sessionId) throws GameNotFoundException {
        GameSession gameSession = gameRepository.findById(sessionId)
                .orElseThrow(() -> new GameNotFoundException("Game session not found"));

        GameSessionGetDTO gameSessionGetDTO = gameSessionMapper.toGetDTO(gameSession);

        Map<Object, Object> scores = redisTemplate.opsForHash().entries("game:" + sessionId);
        Map<UUID, Integer> playerScores = scores.entrySet().stream()
                .filter(e -> e.getKey().toString().startsWith("player:"))
                .collect(Collectors.toMap(
                        e -> UUID.fromString(e.getKey().toString().replaceFirst("player:", "")),
                        e -> (Integer) e.getValue()
                ));
        gameSessionGetDTO.setPlayerScores(playerScores);

        Integer deckSize = (Integer) redisTemplate.opsForValue().get("game:" + sessionId + ":deck");
        gameSessionGetDTO.setDeckSize(deckSize != null ? deckSize : gameSession.getDeck().size());

        Map<UUID, String> playerCodes = new HashMap<>();
        gameSession.getUsers().forEach(user -> {
            String code = (String) redisTemplate.opsForValue().get("game:" + sessionId + ":code:" + user.getUserId());
            playerCodes.put(user.getUserId(), code);
        });
        gameSessionGetDTO.setPlayerCodes(playerCodes);

        gameSessionGetDTO.setNextPlayer(gameSession.getUsers().get(gameSession.getCurrentPlayerIndex()).getName());

        return gameSessionGetDTO;
    }

    private List<CardsGetDTO> generateDeck(List<Cards> cards) {
        cards.add(createPointsCard(1, "Points3", 3, 4));
        cards.add(createPointsCard(2, "Points5", 5, 4));
        cards.add(createPointsCard(3, "Points7", 7, 2));

        cards.add(createActionCard(4, "Block", BLOCK, 1, 2));
        cards.add(createActionCard(5, "Steal", STEAL, 4, 2));
        cards.add(createActionCard(6, "DoubleDown", DOUBLE_DOWN, 2, 1));
        cards.add(createActionCard(7, "Attack", ATTACK, 5, 2));
        cards.add(createActionCard(8, "Defense", DEFENSE, 0, 2));
        cards.add(createActionCard(9, "Heal", HEAL, 5, 2));
        cards.add(createActionCard(10, "Ban", BAN, 1, 1));
        cards.add(createActionCard(11, "ExtraTurn", EXTRA_TURN, 1, 1));
        cards.add(createActionCard(13, "Shuffle", SHUFFLE, 0, 1));

        Collections.shuffle(cards);

        return cardsMapper.toCardsGetDTOs(cards);
    }

    private static boolean isDefended(UUID userId) {
        return defenseStatus.getOrDefault(userId, false);
    }

    private static void activateDefense(UUID userId) {
        defenseStatus.put(userId, true);
    }

    private static void deactivateDefense(UUID userId) {
        defenseStatus.put(userId, false);
    }

    private static void banCardType(TypesActionCards type, int turns) {
        bannedCards.put(type, turns);
    }

    private static boolean isCardTypeBanned(TypesActionCards type) {
        return bannedCards.getOrDefault(type, 0) > 0;
    }

    private static void decrementBanTurns() {
        bannedCards.replaceAll((type, turns) -> turns > 0 ? turns - 1 : 0);
    }

    private static void setExtraTurnForPlayer(UUID userId) {
        extraTurns.add(userId);
    }

    private static boolean hasExtraTurn(UUID userId) {
        if (extraTurns.contains(userId)) {
            extraTurns.remove(userId);
            return true;
        }
        return false;
    }
}
