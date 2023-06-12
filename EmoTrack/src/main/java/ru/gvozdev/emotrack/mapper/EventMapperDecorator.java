package ru.gvozdev.emotrack.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gvozdev.emotrack.dto.EventDto;
import ru.gvozdev.emotrack.entity.EventEntity;
import ru.gvozdev.emotrack.repository.UserRepository;

public abstract class EventMapperDecorator implements EventMapper {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public EventEntity eventDtoToEventEntity(EventDto eventDto) {
        EventEntity eventEntity = eventMapper.eventDtoToEventEntity(eventDto);
        eventEntity.setUser(userRepository.findById(eventDto.getUserId()).get());
        return eventEntity;
    }
}
