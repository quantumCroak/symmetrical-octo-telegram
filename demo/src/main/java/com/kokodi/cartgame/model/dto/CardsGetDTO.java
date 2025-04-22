package com.kokodi.cartgame.model.dto;

import com.kokodi.cartgame.model.enums.TypesActionCards;
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
public class CardsGetDTO {
    private String cardType;
    private Integer cardId;
    private Integer value;
    private String cardName;
    private Integer quantity;
    private Integer actionCardId;
    private Set<TypesActionCards> actionCards;
    private Integer pointsCardId;
    private List<UUID> userIds;
}
