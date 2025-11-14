package projectpractice.tlearnapp.dto;

import projectpractice.tlearnapp.enums.StatsStatus;

public record StatsDto(Long userId, Long wordId, Long attempts, StatsStatus status) {
}
