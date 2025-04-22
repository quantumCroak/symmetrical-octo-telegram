package com.kokodi.cartgame.mapper;

import com.kokodi.cartgame.model.GameSession;
import com.kokodi.cartgame.model.dto.GameSessionCreateDTO;
import com.kokodi.cartgame.model.dto.GameSessionGetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameSessionMapper {

    GameSessionCreateDTO toGetDTOFromCreate(GameSession gameSession);

    GameSessionGetDTO toGetDTO(GameSession gameSession);

    GameSession toEntity(GameSessionGetDTO gameSession);
}
