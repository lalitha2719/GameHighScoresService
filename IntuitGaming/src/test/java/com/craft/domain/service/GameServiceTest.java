package com.craft.domain.service;

import com.craft.domain.model.HighScores;
import com.craft.domain.model.PlayerDetails;
import com.craft.domain.model.PlayerScores;
import com.craft.exception.DataViolationsFoundException;
import com.craft.exception.NoScoresFoundException;
import com.craft.exception.PlayerIdNotFoundException;
import com.craft.infrastructure.entity.Player;
import com.craft.infrastructure.repository.PlayerRepository;
import com.craft.infrastructure.repository.ScoreRepository;
import com.craft.utils.PlayersScoresTestDataSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ScoreRepository scoreRepository;

    @Captor
    ArgumentCaptor<Player> playerCaptor;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Validator spyValidator = spy(validator);

        Field validatorField = GameService.class.getDeclaredField("validator");
        validatorField.setAccessible(true);
        validatorField.set(gameService, spyValidator);
    }


    @Test
    void processPlayerScores_test_scoresPersisted() {
        PlayerScores playerScores = PlayersScoresTestDataSetup.getPlayerScores();
        when(playerRepository.findByPlayerId(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(PlayersScoresTestDataSetup.getPlayerEntity()));
        gameService.processPlayerScores(playerScores);
        Mockito.verify(playerRepository, times(1)).save(playerCaptor.capture());
        Player player = playerCaptor.getValue();
        assertEquals(playerScores.getPlayerId(),player.getPlayerId());
        assertEquals(playerScores.getScore(),player.getScores().get(0).getScore());
    }

    @Test
    void processPlayerScores_test_playerNotFound_in_db() {
        PlayerScores playerScores = PlayersScoresTestDataSetup.getPlayerScores();
        assertThrows(PlayerIdNotFoundException.class,
                ()-> gameService.processPlayerScores(playerScores));
    }

    @Test
    void processPlayerScores_test_dataViolations_found() {
        assertThrows(DataViolationsFoundException.class,
                ()-> gameService.processPlayerScores(new PlayerScores()));
    }

    @Test
    void registerPlayer_test_playerPersisted() {
        PlayerDetails playerDetails = PlayersScoresTestDataSetup.getPlayerDetails();
        gameService.registerPlayer(playerDetails);
        Mockito.verify(playerRepository,times(1)).save(playerCaptor.capture());
        Player player = playerCaptor.getValue();
        assertEquals(playerDetails.getPlayerId(),player.getPlayerId());
        assertEquals(playerDetails.getName(),player.getPlayerName());
    }

    @Test
    void registerPlayer_test_dataViolations_found() {
        assertThrows(DataViolationsFoundException.class,
                ()-> gameService.registerPlayer(new PlayerDetails()));
    }

    @Test
    void getHighScores_test() {
        when(scoreRepository.findTop5ByOrderByScoreDesc())
                .thenReturn(PlayersScoresTestDataSetup.getScoresList());
        List<HighScores> highScores = gameService.getHighScores();
        assertEquals(5,highScores.size());
        assertEquals("Intuit",highScores.get(0).getPlayerName());
    }

    @Test
    void getHighScores_test_emptyScores() {
        assertThrows(NoScoresFoundException.class,()-> gameService.getHighScores());
    }
}
