package com.craft.infrastructure.repository;

import com.craft.infrastructure.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<Player> findByPlayerId(String playerId);

}
