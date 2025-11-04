package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import projectpractice.tlearnapp.dto.responses.GetUserResponse;
import projectpractice.tlearnapp.dto.responses.SetGetWordResponse;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    GetUserResponse toGetUserResponse(User user);

    default SetGetWordResponse toSetGetWordResponse(Set<Word> words) {
        return new SetGetWordResponse(words);
    }
}
