package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.ActionCard;
import com.kokodi.cartgame.model.Cartds;
import com.kokodi.cartgame.model.PointsCard;
import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.CartdsGetDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import com.kokodi.cartgame.model.enums.TypesActionCartds;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CartdsMapper {
//
//    @Named("mapIdsToUsers")
//    public List<User> mapIdsToUsers(List<UUID> userIds) {
//        if (userIds == null) {
//            return null;
//        }
//        return userIds.stream().map(id -> {
//            User user = new User();
//            user.setUserId(id);
//            return user;
//        }).collect(Collectors.toList());
//    }

//    @Named("mapUsersToUserGetDTOs")
//    public List<UserGetDTO> mapUsersToUserGetDTOs(List<User> users) {
//        if (users == null) {
//            return null;
//        }
//        return users.stream().map(user -> {
//            UserGetDTO dto = new UserGetDTO();
//            dto.setUserId(user.getUserId());
//            dto.setUsername(user.getName());
//            return dto;
//        }).collect(Collectors.toList());
//    }

//    public CartdsGetDTO toCartdsGetDTO(Cartds card) {
//        if (card == null) {
//            return null;
//        }
//        CartdsGetDTO dto = new CartdsGetDTO();
//        dto.setCartId(card.getCartId());
//        dto.setValue(card.getValue());
//        dto.setCartName(card.getCardName());
//        dto.setQuantity(card.getQuantity());
//        dto.setUserIds(card.getUsers() != null
//                ? card.getUsers().stream().map(User::getUserId).collect(Collectors.toList())
//                : new ArrayList<>());
//
//        if (card instanceof ActionCard actionCard) {
//            dto.setCardType("ACTION");
//            dto.setActionCardId(actionCard.getActionCardId());
//            dto.setActionCards(actionCard.getActionCartds());
//        } else if (card instanceof PointsCard pointsCard) {
//            dto.setCardType("POINTS");
//            dto.setPointsCardId(pointsCard.getPointsCardId());
//        }
//
//        return dto;
//    }

//    public Cartds toCartds(CartdsGetDTO cardDTO) {
//        if (cardDTO == null) {
//            return null;
//        }
//
//        Cartds card;
//        if ("ACTION".equals(cardDTO.getCardType())) {
//            ActionCard actionCard = new ActionCard();
//            actionCard.setActionCardId(cardDTO.getActionCardId());
//            actionCard.setActionCartds(cardDTO.getActionCards());
//            card = actionCard;
//        } else if ("POINTS".equals(cardDTO.getCardType())) {
//            PointsCard pointsCard = new PointsCard();
//            pointsCard.setPointsCardId(cardDTO.getPointsCardId());
//            card = pointsCard;
//        } else {
//            card = new Cartds();
//        }
//
//        card.setCartId(cardDTO.getCartId());
//        card.setValue(cardDTO.getValue());
//        card.setCardName(cardDTO.getCartName());
//        card.setQuantity(cardDTO.getQuantity());
//        card.setUsers(mapIdsToUsers(cardDTO.getUserIds()));
//
//        return card;
//    }

    public abstract List<CartdsGetDTO> toCartdsGetDTOs(List<Cartds> cards);

    abstract List<Cartds> toCartdsList(List<CartdsGetDTO> cardDTOs);
}
