package com.craft.domain.service;

import com.craft.domain.model.HighScores;
import com.craft.domain.model.PlayerDetails;
import com.craft.domain.model.PlayerScores;
import com.craft.exception.DataViolationsFoundException;
import com.craft.exception.NoScoresFoundException;
import com.craft.exception.PlayerIdNotFoundException;
import com.craft.infrastructure.entity.Player;
import com.craft.infrastructure.entity.Score;
import com.craft.infrastructure.repository.PlayerRepository;
import com.craft.infrastructure.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class GameService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private Validator validator;


    /**
     * Processes and persists the player scores received from game service.
     *
     * @param playerScores the {@link PlayerScores} object containing the player's score data
     * @throws PlayerIdNotFoundException if the player with the given player ID is not found
     * @throws DataViolationsFoundException if there are validation errors in the playerScores object
     */
    public void processPlayerScores(PlayerScores playerScores) {
        log.debug("GameService: processPlayerScores");
        Set<ConstraintViolation<PlayerScores>> violations = validator.validate(playerScores);
        if(violations.isEmpty()) {
            Player player = playerRepository.findByPlayerId(playerScores.getPlayerId()).orElseThrow(()->
                    new PlayerIdNotFoundException("Player with given playerId not found"));
            Score score = new Score();
            score.setPlayer(player);
            score.setScore(playerScores.getScore());
            score.setCreatedDatetime(Instant.now());
            player.getScores().add(score);
            player.setCreatedDatetime(Instant.now());
            player.setUpdatedDatetime(Instant.now());

            playerRepository.save(player);
            log.info("Scores of player with playerId {} persisted",playerScores.getPlayerId());
        }
        else {
            log.error("Data violation found for Player Scores: {}",violations);
            throw new DataViolationsFoundException("Data violation found for Player Scores: "+violations);
        }
    }

    /**
     * Registers or updates a player's details if valid data is received.
     *
     * @param playerDetails the {@link PlayerDetails} object containing the player's details
     * @throws DataViolationsFoundException if there are validation errors in the playerDetails object
     */
    public void registerPlayer(PlayerDetails playerDetails) {
        log.debug("GameService: registerPlayer");
        Set<ConstraintViolation<PlayerDetails>> violations = validator.validate(playerDetails);
        if(violations.isEmpty()) {
            log.info("No Data violations found for player details");
            Player player = playerRepository.findByPlayerId(playerDetails.getPlayerId())
                    .orElse(new Player());
            player.setPlayerId(playerDetails.getPlayerId());
            player.setPlayerName(playerDetails.getName());
            player.setCreatedDatetime(Instant.now());
            player.setUpdatedDatetime(Instant.now());

            playerRepository.save(player);
            log.info("Player details with playerId {} persisted",playerDetails.getPlayerId());
        }
        else {
            log.error("Data violation found for Player Details: {}",violations);
            throw new DataViolationsFoundException("Data violation found for Player Details: "+violations);
        }

    }

    /**
     * Retrieves the top 5 high scores of the game and map the details to HighScores objects
     *
     * @return a list of {@link HighScores} objects representing the top 5 high scores
     * @throws NoScoresFoundException if no scores are found in the repository
     */
    public List<HighScores> getHighScores() {
        log.debug("GameService: getHighScores");
        log.info("Fetching top 5 scores of the game");
        List<Score> scores = scoreRepository.findTop5ByOrderByScoreDesc();
        List<HighScores> highScores = new ArrayList<>();
        scores.forEach((score) ->{
            HighScores highScore = new HighScores();
            highScore.setScore(score.getScore());
            highScore.setPlayerName(score.getPlayer().getPlayerName());
            highScores.add(highScore);
        });
       if(highScores.isEmpty()){
           throw new NoScoresFoundException("No scores received for players");
       }
        return highScores;
    }
}
