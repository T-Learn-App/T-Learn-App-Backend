package projectpractice.tlearnapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.dto.StatsDto;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;

@Mapper(componentModel = "spring")
public interface StatsMapper {

     @Mapping(source = "user.id", target = "userId")
     @Mapping(source = "word.id", target = "wordId")
     StatsDto toStatsDto(Stat stat);
}
