package ru.gvozdev.emotrack.listners;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gvozdev.emotrack.service.EventService;

@Component
@RequiredArgsConstructor
public class KafkaListner {

    private final EventService eventService;
    @KafkaListener(topics = "predicted_queue", groupId = "test")
    public void listenGroupFoo(String message) throws JsonProcessingException {
        eventService.setPredictedValueFromKafka(message);
    }
}
