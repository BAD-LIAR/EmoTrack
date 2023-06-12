package ru.gvozdev.emotrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gvozdev.emotrack.entity.EventEntity;
import ru.gvozdev.emotrack.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
