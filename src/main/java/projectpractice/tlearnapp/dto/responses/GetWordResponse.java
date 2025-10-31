package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public record GetWordResponse(@JsonProperty String word,
                              @JsonProperty String transcription,
                              @JsonProperty String translation,
                              @JsonProperty String partOfSpeech) {
}
