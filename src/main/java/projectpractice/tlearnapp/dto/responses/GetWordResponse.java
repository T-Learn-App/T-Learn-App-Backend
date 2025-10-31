package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;


public record GetWordResponse(@JsonProperty String word,
                              @JsonProperty String transcription,
                              @JsonProperty String translation,
                              @JsonProperty String partOfSpeech) {
}
