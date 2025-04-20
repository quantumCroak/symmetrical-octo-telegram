package com.kokodi.cartgame.model.dto;

import com.kokodi.cartgame.model.User;
import com.kokodi.cartgame.model.enums.StatusSessionGame;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
