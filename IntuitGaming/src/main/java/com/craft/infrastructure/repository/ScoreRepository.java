package com.craft.infrastructure.repository;

import com.craft.infrastructure.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findTop5ByOrderByScoreDesc();

}
