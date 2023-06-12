package ru.gvozdev.emotrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.structure.core.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gvozdev.emotrack.dto.EventDto;
import ru.gvozdev.emotrack.dto.KafkaEventDto;
import ru.gvozdev.emotrack.dto.PredictedValueDto;
import ru.gvozdev.emotrack.entity.EventEntity;
import ru.gvozdev.emotrack.mapper.EventMapper;
import ru.gvozdev.emotrack.repository.EventRepository;
import ru.gvozdev.emotrack.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;


    public EventEntity createEvent(EventDto eventDto) throws IOException {
        EventEntity eventEntity = eventMapper.eventDtoToEventEntity(eventDto);
        eventEntity = eventRepository.save(eventEntity);
        kafkaService.sendEventToPrediction(eventEntity);
        return eventEntity;
    }

    public EventDto getEvent(Long eventId){
        EventEntity EventEntity = eventRepository.findById(eventId).orElseThrow(IllegalArgumentException::new);
        return eventMapper.eventEntityToEventDto(EventEntity);
    }

    public List<EventDto> getUserEventsByDay(Long userId, Date day){
        List<EventEntity> eventEntities = eventRepository.getUserEventsByDay(userId, day);
        return eventEntities.stream()
                .map(eventMapper::eventEntityToEventDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getUserEvents(Long userId){
        List<EventEntity> eventEntities = eventRepository.getEventEntitiesByUserIdOrderByStartDateDescWithLimit(userId, 30L);
        return eventEntities.stream()
                .map(eventMapper::eventEntityToEventDto)
                .collect(Collectors.toList());
    }

    public void setPredictedValueFromKafka(String json) throws JsonProcessingException {
        PredictedValueDto predictedValueDto = objectMapper.readValue(json, PredictedValueDto.class);
        EventEntity eventEntity = eventRepository.findById(predictedValueDto.getId()).get();
        eventEntity.setPredictedEmotionalCondition(predictedValueDto.getValue());
        eventRepository.save(eventEntity);
    }
}
