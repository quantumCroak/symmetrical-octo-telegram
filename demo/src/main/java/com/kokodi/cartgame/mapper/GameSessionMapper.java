package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.Cartds;
import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.dto.CartdsGetDTO;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import com.kokodi.cartgame.model.dto.UserGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameSessionMapper {

//    @Mapping(target = "statusSessionGame", source = "statusSessionGame")
//    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsersToUserGetDTOs")
//    @Mapping(target = "deck", source = "deck", qualifiedByName = "mapDeckToDTOList")
    GameSessionCreateDTO toGetDTOFromCreate(GameSession gameSession);

//    @Mapping(target = "statusSessionGame", source = "statusSessionGame")
//    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsersToUserGetDTOs")
//    @Mapping(target = "deck", source = "deck", qualifiedByName = "mapDeckToDTOList")
    GameSessionGetDTO toGetDTO(GameSession gameSession);

//    @Mapping(target = "gameSessionId", ignore = true)
//    @Mapping(target = "users", source = ".", qualifiedByName = "mapCreateDTOToUsers")
//    @Mapping(target = "statusSessionGame", source = "statusSessionGame")
//    @Mapping(target = "deck", ignore = true)
//    @Mapping(target = "turns", ignore = true)
//    @Mapping(target = "playerScores", ignore = true)
//    @Mapping(target = "skipTurns", ignore = true)
    GameSession toEntity(GameSessionCreateDTO createDTO);

//    @Named("mapUsersToUserGetDTOs")
//    default List<UserGetDTO> mapUsersToUserGetDTOs(List<User> users) {
//        return users != null ? users.stream().map(user -> {
//            UserGetDTO dto = new UserGetDTO();
//            dto.setUserId(user.getUserId());
//            dto.setUsername(user.getName());
//            return dto;
//        }).collect(Collectors.toList()) : null;
//    }

//    @Named("mapDeckToDTOList")
//    default List<CartdsGetDTO> mapDeckToDTOList(List<Cartds> deck) {
//        if (deck == null) {
//            return null;
//        }
//        CartdsGetDTO dto = new CartdsGetDTO();
//        dto.setUnusedCartds(deck);
//        dto.setUsedCartds(new ArrayList<>());
//        dto.setUsers(new ArrayList<>());
//        dto.setCart(null);
//        return List.of(dto);
//    }

//    @Named("mapCreateDTOToUsers")
//    default List<User> mapCreateDTOToUsers(GameSessionCreateDTO createDTO) {
//        User user = new User();
//        user.setUserId(createDTO.setUser());
//        user.setName(String.valueOf(createDTO.getUser()));
//        return List.of(user);
//    }
}
