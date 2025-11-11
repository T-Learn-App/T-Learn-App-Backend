package projectpractice.tlearnapp.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatsStatus {
    DRAFT,
    IN_PROGRESS,
    COMPLETED;

    public static StatsStatus getStatus(int attempts) {
        if (attempts == 0) {
            return DRAFT;
        } else if (attempts >= 1 && attempts <= 2) {
            return IN_PROGRESS;
        } else {
            return COMPLETED;
        }
    }
}
