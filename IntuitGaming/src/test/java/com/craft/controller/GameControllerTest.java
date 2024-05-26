package com.craft.controller;

import com.craft.domain.service.GameService;
import com.craft.infrastructure.listener.PlayerListener;
import com.craft.infrastructure.listener.ScoresListener;
import com.craft.utils.PlayersScoresTestDataSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerListener playerListener;

    @MockBean
    private ScoresListener scoresListener;

    @InjectMocks
    private GameController gameController;

    @Test
    public void testGetHighScores() throws Exception {
        String highScoresJson = "[{\"playerName\":\"player1\",\"score\":100},{\"playerName\":\"player2\",\"score\":200}]";
        when(gameService.getHighScores()).thenReturn(PlayersScoresTestDataSetup.getHighScores());
        mockMvc.perform(MockMvcRequestBuilders.get("/highScores"))
                .andExpect(status().isOk())
                .andExpect(content().json(highScoresJson));
    }
}
