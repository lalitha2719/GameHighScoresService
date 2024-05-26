package com.craft.infrastructure.listener;

import com.craft.domain.model.PlayerScores;
import com.craft.domain.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ScoresListener {

    @Autowired
    private GameService gameService;

    @KafkaListener(topics = "${kafka.topic.score-topic-name}", groupId = "${kafka.topic.group-id}")
    public void onMessage(ConsumerRecord<String, String> message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PlayerScores playerScores = objectMapper.readValue(message.value(), PlayerScores.class);
        gameService.processPlayerScores(playerScores);
    }
}