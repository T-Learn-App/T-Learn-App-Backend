package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import projectpractice.tlearnapp.dto.WordResponse;
import projectpractice.tlearnapp.entities.Word;

@Mapper(componentModel = "spring")
public interface WordMapper {

    WordResponse toWordResponse(Word entity);
}
