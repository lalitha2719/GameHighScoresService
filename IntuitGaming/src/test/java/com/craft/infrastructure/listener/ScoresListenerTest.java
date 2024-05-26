package com.craft.infrastructure.listener;

import com.craft.domain.model.PlayerScores;
import com.craft.domain.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScoresListenerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private ScoresListener scoresListener;

    @Spy
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testOnMessage() throws JsonProcessingException {
        String scoreJson = "{\"playerId\":\"saij\",\"score\":100}";
        ConsumerRecord<String, String> record = new ConsumerRecord<>("score-topic", 0, 0L, null, scoreJson);
        scoresListener.onMessage(record);

        verify(gameService).processPlayerScores(any(PlayerScores.class));
    }
}
