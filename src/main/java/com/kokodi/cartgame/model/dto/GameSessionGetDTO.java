package com.kokodi.cartgame.model.dto;

import com.kokodi.cartgame.model.enums.StatusSessionGame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameSessionGetDTO {
    UUID sessionId;
    List<UserGetDTO> users;
    List<CartdsGetDTO> deck;
    StatusSessionGame statusSessionGame;
    private Map<UUID, Integer> playerScores;
    private int deckSize;
    private Map<UUID, String> playerCodes;
    private String nextPlayer;
}
