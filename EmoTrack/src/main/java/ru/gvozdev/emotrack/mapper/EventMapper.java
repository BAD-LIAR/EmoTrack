package ru.gvozdev.emotrack.mapper;

import org.mapstruct.Builder;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gvozdev.emotrack.dto.EventDto;
import ru.gvozdev.emotrack.dto.KafkaEventDto;
import ru.gvozdev.emotrack.entity.EventEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
@DecoratedWith(EventMapperDecorator.class)
public interface EventMapper {

    @Mapping(ignore = true, target = "user")
    EventEntity eventDtoToEventEntity(EventDto userDto);

    EventDto eventEntityToEventDto(EventEntity userEntity);
    KafkaEventDto eventEntityToKafkaEventDto(EventEntity eventEntity);
}
