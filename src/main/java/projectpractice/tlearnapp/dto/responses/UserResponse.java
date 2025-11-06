package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record UserResponse(@JsonProperty Long id, @JsonProperty String email) implements Serializable {
}
