package com.kokodi.cartgame.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    UUID userId;
    @Column(nullable = false)
    String name;
    String password;
    String login;
    @ManyToMany
    List<Cartds> cartds;
    @ManyToOne
    GameSession gameSession;
//    Integer moves;
//    Integer points;
//    @ElementCollection
//    List<Cartds> cartds;
}
