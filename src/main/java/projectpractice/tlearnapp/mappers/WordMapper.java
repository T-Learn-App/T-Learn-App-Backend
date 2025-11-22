package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import projectpractice.tlearnapp.dto.responses.WordResponse;
import projectpractice.tlearnapp.entities.Word;

@Mapper(componentModel = "spring")
public interface WordMapper {

    WordResponse toWordResponse(Word entity);
}
