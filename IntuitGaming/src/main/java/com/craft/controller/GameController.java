package com.craft.controller;

import com.craft.domain.model.HighScores;
import com.craft.domain.model.PlayerDetails;
import com.craft.domain.model.PlayerScores;
import com.craft.domain.service.GameService;
import com.craft.infrastructure.listener.PlayerListener;
import com.craft.infrastructure.listener.ScoresListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
@Api(produces = "application/json", value = "Manages Players and Scores")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerListener playerListener;

    @Autowired
    private ScoresListener scoresListener;


    @CrossOrigin
    @GetMapping(value = "/highScores")
    public List<HighScores> getHighScores()  {
        return gameService.getHighScores();
    }

    @CrossOrigin
    @ApiOperation(value = "save player details", response = String.class)
    @PostMapping(value = "/registerPlayer")
    public String callPlayerListener(@RequestBody PlayerDetails playerDetails) throws JsonProcessingException {
        String jsonString = new ObjectMapper().writeValueAsString(playerDetails);
        ConsumerRecord<String, String> playerRecord = new ConsumerRecord<>("player-topic", 0, 0L, null, jsonString);
        playerListener.onMessage(playerRecord);
        return "Player Created/Updated at "+Instant.now();
    }

    @CrossOrigin
    @ApiOperation(value = "save score details", response = List.class)
    @PostMapping(value = "/addPlayerScore")
    public String callScoreListener(@RequestBody PlayerScores playerScores) throws JsonProcessingException {
        String jsonString = new ObjectMapper().writeValueAsString(playerScores);
        ConsumerRecord<String, String> scoreRecord = new ConsumerRecord<>("score-topic", 0, 0L, null, jsonString);
        scoresListener.onMessage(scoreRecord);
        return "Scores updated for Player with ID "+ playerScores.getPlayerId()+" updated";
    }

}
