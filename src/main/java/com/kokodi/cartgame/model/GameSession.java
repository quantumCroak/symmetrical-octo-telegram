package com.kokodi.cartgame.model;

import com.kokodi.cartgame.model.enums.StatusSessionGame;
import com.kokodi.cartgame.model.enums.TypesActionCartds;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GameSession {
    @Id
    @GeneratedValue
    UUID gameSessionId;
    @ManyToMany
    List<User> users;
    StatusSessionGame statusSessionGame;
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    List<Turns> turns = new ArrayList<>();
    @ElementCollection
    Map<Integer, Integer> playerScores;
    Integer skipTurns;
    @ManyToMany
    List<Cartds> deck = new ArrayList<>();
    int currentPlayerIndex;
    static Map<UUID, Boolean> defenseStatus = new HashMap<>();
    static Map<TypesActionCartds, Integer> bannedCards = new HashMap<>();
    static List<UUID> extraTurns = new ArrayList<>();

    public static boolean isDefended(UUID userId) {
        return defenseStatus.getOrDefault(userId, false);
    }

    public static void activateDefense(UUID userId) {
        defenseStatus.put(userId, true);
    }

    public static void deactivateDefense(UUID userId) {
        defenseStatus.put(userId, false);
    }

    public static void banCardType(TypesActionCartds type, int turns) {
        bannedCards.put(type, turns);
    }

    public static boolean isCardTypeBanned(TypesActionCartds type) {
        return bannedCards.getOrDefault(type, 0) > 0;
    }

    public static void decrementBanTurns() {
        bannedCards.replaceAll((type, turns) -> turns > 0 ? turns - 1 : 0);
    }

    public static void setExtraTurnForPlayer(UUID userId) {
        extraTurns.add(userId);
    }

    public static boolean hasExtraTurn(UUID userId) {
        if (extraTurns.contains(userId)) {
            extraTurns.remove(userId);
            return true;
        }
        return false;
    }
}
