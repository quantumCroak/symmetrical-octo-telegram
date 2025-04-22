package com.kokodi.cartgame.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turns")
public class Turns {
    @Id
    @GeneratedValue
    @Column(name = "turn_id")
    private UUID turnsId;

    @Column(name = "active_user_id")
    private UUID activeUserId;

    @ManyToOne
    @JoinColumn(name = "game_session_id")
    private GameSession gameSession;

    @Column(name = "action")
    private String action;
}
