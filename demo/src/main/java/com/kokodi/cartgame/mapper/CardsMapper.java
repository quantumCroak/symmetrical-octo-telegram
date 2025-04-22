package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.Cards;
import com.kokodi.cartgame.model.dto.CardsGetDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CardsMapper {

    public abstract List<CardsGetDTO> toCardsGetDTOs(List<Cards> cards);

    abstract List<Cards> toCartdsList(List<CardsGetDTO> cardDTOs);
}
