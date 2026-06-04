package projectpractice.tlearnapp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import projectpractice.tlearnapp.enums.StatsStatus;

@Builder
public record StatsDto (
        @Getter
    Long userId,
        @Getter
        Long wordId,
        @Getter
        Long attempts,
        @Getter
        StatsStatus status,
        @Getter
        Long lastDays
) {}
