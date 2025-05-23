package com.kokodi.cartgame.model.dto;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.enums.StatusSessionGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameSessionCreateDTO {
    UUID sessionId;
    User user;
    StatusSessionGame statusSessionGame;
}
