package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import projectpractice.tlearnapp.dto.responses.UserResponse;
import projectpractice.tlearnapp.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

}
