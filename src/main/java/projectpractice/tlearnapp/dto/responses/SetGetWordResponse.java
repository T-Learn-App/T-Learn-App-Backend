package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import projectpractice.tlearnapp.entities.Word;

import java.io.Serializable;
import java.util.Set;

public record SetGetWordResponse(@JsonProperty Set<Word> words) implements Serializable {
}
