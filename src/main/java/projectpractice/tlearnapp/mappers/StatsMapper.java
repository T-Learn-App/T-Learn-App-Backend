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

     StatsDto toStatsDto(Stat stat);

//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "word", ignore = true)
//    @Mapping(target = "status", expression = "java(projectpractice.tlearnapp.enums.StatQueueStatus.ACCEPTED)")
//    @Mapping(target = "error", ignore = true)
//    StatQueue toStatQueueHelper(StatQueueDto statQueueDto);
//
//    default StatQueue toStatQueue(StatQueueDto statQueueDto, User user, Word word) {
//        StatQueue entity = toStatQueueHelper(statQueueDto);
//        entity.setUser(user);
//        entity.setWord(word);
//        return entity;
//    }
}
