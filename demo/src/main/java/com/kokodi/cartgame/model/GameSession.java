package com.kokodi.cartgame.model;

import com.kokodi.cartgame.model.enums.StatusSessionGame;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_sessions")
public class GameSession {
    @Id
    @GeneratedValue
    @Column(name = "game_session_id")
    private UUID gameSessionId;

    @ManyToMany
    @JoinTable(
            name = "game_session_users",
            joinColumns = @JoinColumn(name = "game_session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_session_game")
    StatusSessionGame statusSessionGame;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_session_id")
    List<Turns> turns = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "player_scores", joinColumns = @JoinColumn(name = "game_session_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "score")
    Map<UUID, Integer> playerScores;

    @Column(name = "skip_turns")
    Integer skipTurns;

    @ManyToMany
    @JoinTable(
            name = "game_session_deck",
            joinColumns = @JoinColumn(name = "game_session_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    List<Cards> deck = new ArrayList<>();

    @Column(name = "current_player_index")
    int currentPlayerIndex;
}
