package com.kokodi.cartgame.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PointsCard extends Cartds{
    @Id
    int pointsCardId;

    public static PointsCard createPointsCard(int cartId, String cartName, int value, int quantity) {
        PointsCard card = new PointsCard();
        card.setCartId(cartId);
        card.setCartName(cartName);
        card.setValue(value);
        card.setQuantity(quantity);
        card.setPointsCardId(cartId);
        card.setUsers(new ArrayList<>());
        return card;
    }
}
