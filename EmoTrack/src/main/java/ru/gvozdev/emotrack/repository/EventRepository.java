package ru.gvozdev.emotrack.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gvozdev.emotrack.entity.EventEntity;
import ru.gvozdev.emotrack.entity.UserEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<EventEntity, Long> {

    @Query(value = "select * from event where user_id = :userEntity and DATE(start_date) = DATE(:startDate)",
            nativeQuery = true)
    List<EventEntity> getUserEventsByDay(Long userEntity, Date startDate);

    @Query(nativeQuery = true, value = "select * from event where user_id = :userEntity order by start_date desc limit :limit")
    List<EventEntity> getEventEntitiesByUserIdOrderByStartDateDescWithLimit(Long userEntity, Long limit);

}
