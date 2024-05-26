package com.craft.infrastructure.listener;

import com.craft.domain.model.PlayerDetails;
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
public class PlayerListenerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private PlayerListener playerListener;

    @Spy
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testOnMessage() throws JsonProcessingException {
        String playerJson = "{\"playerId\":\"saij\",\"playerName\":\"saijasti\",\"age\":24,\"gender\":\"FEMALE\"}";
        ConsumerRecord<String, String> record = new ConsumerRecord<>("player-topic", 0, 0L, null, playerJson);
        playerListener.onMessage(record);

        verify(gameService).registerPlayer(any(PlayerDetails.class));
    }
}
