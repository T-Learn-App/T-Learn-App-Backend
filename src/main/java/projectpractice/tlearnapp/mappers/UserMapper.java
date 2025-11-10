package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import projectpractice.tlearnapp.dto.UserDto;
import projectpractice.tlearnapp.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

}
