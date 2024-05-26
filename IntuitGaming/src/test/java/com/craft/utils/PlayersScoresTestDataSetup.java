package com.craft.utils;

import com.craft.domain.enums.GenderEnum;
import com.craft.domain.model.HighScores;
import com.craft.domain.model.PlayerDetails;
import com.craft.domain.model.PlayerScores;
import com.craft.infrastructure.entity.Player;
import com.craft.infrastructure.entity.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayersScoresTestDataSetup {

    public static PlayerScores getPlayerScores() {
        return PlayerScores.builder()
                .playerId("playerId")
                .score(100)
                .build();
    }

    public static Player getPlayerEntity() {
        Player player = new Player();
        player.setPlayerUuid(1);
        player.setPlayerId("playerId");

        return player;
    }

    public static PlayerDetails getPlayerDetails() {
        return PlayerDetails.builder()
                .playerId("playerId")
                .age(22)
                .name("Lalitha")
                .gender(GenderEnum.FEMALE)
                .build();
    }

    public static List<Score> getScoresList() {
        List<Score> scores = new ArrayList<>();
        List<String> names = Arrays.asList("Intuit","Lalitha","Tony","Stark","IronMan");
        for(int i=0;i<5;i++) {
            Score score = new Score();
            score.setScore(new Random().nextInt(80) + 100);
            score.setPlayer(Player.builder().playerName(names.get(i)).build());
            scores.add(score);
        }
        return scores;
    }

    public static List<HighScores> getHighScores() {
        HighScores score1 = new HighScores("player1", 100);
        HighScores score2 = new HighScores("player2", 200);
        List<HighScores> highScores = Arrays.asList(score1, score2);
        return highScores;
    }

}
