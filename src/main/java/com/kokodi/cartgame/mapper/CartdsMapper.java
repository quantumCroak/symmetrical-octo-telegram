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

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartdsMapper {
    CartdsMapper INSTANCE = Mappers.getMapper(CartdsMapper.class);

    @Mapping(target = "userIds", source = "users", qualifiedByName = "mapUsersToIds")
    @Mapping(target = "cardType", expression = "java(cartds instanceof ActionCard ? \"action\" : \"points\")")
    @Mapping(target = "actionCards", source = ".", qualifiedByName = "mapActionCards")
    @Mapping(target = "actionCardId", source = ".", qualifiedByName = "mapActionCardId")
    @Mapping(target = "pointsCardId", source = ".", qualifiedByName = "mapPointsCardId")
    @Mapping(target = "usedCartds", ignore = true)
    @Mapping(target = "unusedCartds", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsersToUserGetDTOs")
    CartdsGetDTO toDTO(Cartds cartds);

    List<CartdsGetDTO> toDTOList(List<Cartds> cards);

    @Mapping(target = "users", source = "userIds", qualifiedByName = "mapIdsToUsers")
    Cartds toEntity(CartdsGetDTO cardDTO);

    @Named("mapUsersToIds")
    default List<UUID> mapUsersToIds(List<User> users) {
        return users != null ? users.stream().map(User::getUserId).collect(Collectors.toList()) : null;
    }

    @Named("mapIdsToUsers")
    default List<User> mapIdsToUsers(List<UUID> userIds) {
        return userIds != null ? userIds.stream().map(id -> {
            User user = new User();
            user.setUserId(id);
            return user;
        }).collect(Collectors.toList()) : null;
    }

    @Named("mapUsersToUserGetDTOs")
    default List<UserGetDTO> mapUsersToUserGetDTOs(List<User> users) {
        return users != null ? users.stream().map(user -> {
            UserGetDTO dto = new UserGetDTO();
            dto.setUserId(user.getUserId());
            dto.setUsername(user.getName());
            return dto;
        }).collect(Collectors.toList()) : null;
    }

    @Named("mapActionCards")
    default Set<TypesActionCartds> mapActionCards(Cartds cartds) {
        if (cartds instanceof ActionCard) {
            return ((ActionCard) cartds).getActionCartds();
        }
        return null;
    }

    @Named("mapActionCardId")
    default Integer mapActionCardId(Cartds cartds) {
        if (cartds instanceof ActionCard) {
            return ((ActionCard) cartds).getActionCardId();
        }
        return null;
    }

    @Named("mapPointsCardId")
    default Integer mapPointsCardId(Cartds cartds) {
        if (cartds instanceof PointsCard) {
            return ((PointsCard) cartds).getPointsCardId();
        }
        return null;
    }

    @AfterMapping
    default void setCardType(@MappingTarget Cartds cartds, CartdsGetDTO cardDTO) {
        if ("action".equals(cardDTO.getCardType())) {
            ActionCard actionCard = (ActionCard) cartds;
            actionCard.setActionCardId(cardDTO.getActionCardId());
            actionCard.setActionCartds(cardDTO.getActionCards());
        } else if ("points".equals(cardDTO.getCardType())) {
            PointsCard pointsCard = (PointsCard) cartds;
            pointsCard.setPointsCardId(cardDTO.getPointsCardId());
        }
    }

    @Named("toActionCard")
    default ActionCard toActionCard(CartdsGetDTO cardDTO) {
        if ("action".equals(cardDTO.getCardType())) {
            ActionCard card = new ActionCard();
            card.setCartId(cardDTO.getCartId());
            card.setValue(cardDTO.getValue());
            card.setCartName(cardDTO.getCartName());
            card.setQuantity(cardDTO.getQuantity());
            card.setActionCardId(cardDTO.getActionCardId());
            card.setActionCartds(cardDTO.getActionCards());
            card.setUsers(mapIdsToUsers(cardDTO.getUserIds()));
            return card;
        }
        return null;
    }

    @Named("toPointsCard")
    default PointsCard toPointsCard(CartdsGetDTO cardDTO) {
        if ("points".equals(cardDTO.getCardType())) {
            PointsCard card = new PointsCard();
            card.setCartId(cardDTO.getCartId());
            card.setValue(cardDTO.getValue());
            card.setCartName(cardDTO.getCartName());
            card.setQuantity(cardDTO.getQuantity());
            card.setPointsCardId(cardDTO.getPointsCardId());
            card.setUsers(mapIdsToUsers(cardDTO.getUserIds()));
            return card;
        }
        return null;
    }
}
