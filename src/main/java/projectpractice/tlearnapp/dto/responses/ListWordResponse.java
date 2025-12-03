package projectpractice.tlearnapp.dto.responses;

import java.util.List;

public record ListWordResponse(Long userId, List<WordResponse> words) {
}
