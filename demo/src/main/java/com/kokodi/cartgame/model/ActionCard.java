package com.kokodi.cartgame.model;

import com.kokodi.cartgame.model.enums.TypesActionCards;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "action_cartds")
public class ActionCard extends Cards{
    @Column(name = "action_card_id")
    private int actionCardId;

    @ElementCollection
    @CollectionTable(name = "action_card_types", joinColumns = @JoinColumn(name = "action_card_id"))
    @Column(name = "action_type")
    private Set<TypesActionCards> actionCards;

    public static ActionCard createActionCard(int actionCardId, String cartName, TypesActionCards actionCards, int value, int quantity) {
        ActionCard card = new ActionCard();
        card.setCardId(actionCardId);
        card.setCardName(cartName);
        card.setValue(value);
        card.setQuantity(quantity);
        card.setActionCardId(actionCardId);
        card.setActionCards(Collections.singleton(actionCards));
        card.setUsers(new ArrayList<>());
        return card;
    }
}
