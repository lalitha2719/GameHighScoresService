package com.craft.infrastructure.listener;

import com.craft.domain.model.PlayerDetails;
import com.craft.domain.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PlayerListener {

    @Autowired
    private GameService gameService;

    @KafkaListener(topics = "${kafka.topic.player-topic-name}", groupId = "${kafka.topic.group-id}")
    public void onMessage(ConsumerRecord<String, String> message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PlayerDetails playerDetails = mapper.readValue(message.value(), PlayerDetails.class);
        gameService.registerPlayer(playerDetails);
    }
}