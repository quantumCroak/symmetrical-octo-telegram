package com.kokodi.cartgame.model;

import com.kokodi.cartgame.model.enums.TypesActionCartds;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class ActionCard extends Cartds{
    int ActionCardId;
    @ElementCollection
    Set<TypesActionCartds> actionCartds;

    public static ActionCard createActionCard(int actionCardId, String cartName, TypesActionCartds actionCartds, int value, int quantity) {
        ActionCard card = new ActionCard();
        card.setCartId(actionCardId);
        card.setCartName(cartName);
        card.setValue(value);
        card.setQuantity(quantity);
        card.setActionCardId(actionCardId);
        card.setActionCartds(Collections.singleton(actionCartds));
        card.setUsers(new ArrayList<>());
        return card;
    }
}
