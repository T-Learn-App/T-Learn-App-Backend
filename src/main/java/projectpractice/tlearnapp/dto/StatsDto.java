package projectpractice.tlearnapp.dto;

import lombok.Builder;
import lombok.Data;
import projectpractice.tlearnapp.enums.StatsStatus;

@Data
@Builder
public class StatsDto {
    private Long userId;
    private Long wordId;
    private Long attempts;
    private StatsStatus status;
    private Long lastDays;
}
