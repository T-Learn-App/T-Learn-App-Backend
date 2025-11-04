package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import projectpractice.tlearnapp.entities.Word;

import java.io.Serializable;
import java.util.Set;

public record GetUserResponse(@JsonProperty Long id,
                                  @JsonProperty String username,
                                  @JsonProperty String userLastname,
                                  @JsonProperty Set<Word> userLearntWords) implements Serializable {
}
