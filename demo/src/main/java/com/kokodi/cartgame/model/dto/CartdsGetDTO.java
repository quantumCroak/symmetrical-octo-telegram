package com.kokodi.cartgame.model.dto;

import com.kokodi.cartgame.model.Cartds;
import com.kokodi.cartgame.model.enums.TypesActionCartds;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartdsGetDTO {
    List<Cartds> usedCartds;
    List<Cartds> unusedCartds;
    Cartds cart;
    List<UserGetDTO> users;
    String cardType;
    Integer actionCardId;
    Set<TypesActionCartds> actionCards;
    Integer pointsCardId;
    Integer cartId;
    Integer value;
    String cartName;
    Integer quantity;
    List<UUID> userIds;
}
