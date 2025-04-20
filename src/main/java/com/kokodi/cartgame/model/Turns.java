package com.kokodi.cartgame.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.smartcardio.Card;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turns {
    @Id
    @GeneratedValue
    UUID turnsId;
    UUID activeUserId;
    @ElementCollection
    @ManyToMany
    List<User> users;
    @OneToMany
    List<Cartds> cards;
    @ManyToOne(cascade = CascadeType.ALL)
    GameSession gameSession;
    String action;
}
