package com.kokodi.cartgame.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Cartds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer cartId;
    Integer value;
    String cartName;
    Integer quantity;
    @ManyToMany
    List<User> users;
}
