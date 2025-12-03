package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projectpractice.tlearnapp.dto.responses.WordResponse;
import projectpractice.tlearnapp.entities.Word;

@Mapper(componentModel = "spring")
public interface WordMapper {

    @Mapping(source = "category.id", target = "category")
    WordResponse toWordResponse(Word entity);
}
