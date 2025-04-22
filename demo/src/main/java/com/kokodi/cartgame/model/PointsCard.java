package com.kokodi.cartgame.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "points_cards")
public class PointsCard extends Cards{
    @Column(name = "points_card_id")
    private int pointsCardId;

    public static PointsCard createPointsCard(int pointsCardId, String cardName, int value, int quantity) {
        PointsCard card = new PointsCard();
        card.setCardId(pointsCardId);
        card.setCardName(cardName);
        card.setValue(value);
        card.setQuantity(quantity);
        card.setPointsCardId(pointsCardId);
        card.setUsers(new ArrayList<>());
        return card;
    }
}
