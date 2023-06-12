package ru.gvozdev.emotrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.gvozdev.emotrack.dto.KafkaEventDto;
import ru.gvozdev.emotrack.entity.EventEntity;
import ru.gvozdev.emotrack.mapper.EventMapper;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value(value = "${spring.kafka.to-predict-topic}")
    private String toPredictTopic;
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;

    public void sendEventToPrediction(EventEntity eventEntity) throws JsonProcessingException {
        KafkaEventDto kafkaEventDto = eventMapper.eventEntityToKafkaEventDto(eventEntity);
        String kafkaEventJson = objectMapper.writeValueAsString(kafkaEventDto);
        kafkaTemplate.send(toPredictTopic, kafkaEventJson);
    }


}
