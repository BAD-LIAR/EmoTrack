package ru.gvozdev.emotrack.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ru.gvozdev.emotrack.dto.UserDto;
import ru.gvozdev.emotrack.entity.UserEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {


    UserEntity userDtoToUserEntity(UserDto userDto);

    UserDto userEntityToUserDto(UserEntity userEntity);
}
