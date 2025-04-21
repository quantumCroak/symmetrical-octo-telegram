package com.kokodi.cartgame.model;

import com.kokodi.cartgame.model.enums.TypesActionCartds;
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
public class ActionCard extends Cartds{
    @Column(name = "action_card_id")
    int ActionCardId;

    @ElementCollection
    @CollectionTable(name = "action_card_types", joinColumns = @JoinColumn(name = "action_card_id"))
    @Column(name = "action_type")
    Set<TypesActionCartds> actionCartds;

    public static ActionCard createActionCard(int actionCardId, String cartName, TypesActionCartds actionCartds, int value, int quantity) {
        ActionCard card = new ActionCard();
        card.setCartId(actionCardId);
        card.setCardName(cartName);
        card.setValue(value);
        card.setQuantity(quantity);
        card.setActionCardId(actionCardId);
        card.setActionCartds(Collections.singleton(actionCartds));
        card.setUsers(new ArrayList<>());
        return card;
    }
}
