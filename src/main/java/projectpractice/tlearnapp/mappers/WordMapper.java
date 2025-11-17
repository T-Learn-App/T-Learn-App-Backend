package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.entities.Word;

@Mapper(componentModel = "spring")
public interface WordMapper {

    GetWordResponse toGetWordResponse(Word entity);
}
