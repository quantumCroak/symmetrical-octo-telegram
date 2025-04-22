package com.kokodi.cartgame.repository;

import com.kokodi.cartgame.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<GameSession, UUID> {
}
