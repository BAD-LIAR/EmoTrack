package ru.gvozdev.emotrack.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import ru.gvozdev.emotrack.dto.UserDto;
import ru.gvozdev.emotrack.entity.UserEntity;
import ru.gvozdev.emotrack.mapper.UserMapper;
import ru.gvozdev.emotrack.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserEntity createUser(UserDto userDto){
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        return userRepository.save(userEntity);
    }

    public UserDto getUser(Long userId){
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        return userMapper.userEntityToUserDto(userEntity);
    }
}
