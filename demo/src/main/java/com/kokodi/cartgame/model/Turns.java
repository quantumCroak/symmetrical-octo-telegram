package com.kokodi.cartgame.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
    UUID turnsId;

    @Column(name = "active_user_id")
    UUID activeUserId;

//    @ElementCollection
//    @ManyToMany
//    List<User> users;

//    @OneToMany
//    List<Cartds> cards;

    @ManyToOne
    @JoinColumn(name = "game_session_id")
    GameSession gameSession;

    @Column(name = "action")
    String action;
}
