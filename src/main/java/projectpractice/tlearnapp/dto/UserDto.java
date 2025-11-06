package projectpractice.tlearnapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserDto(@JsonProperty Long id,
                      @JsonProperty @Email @NotBlank String email) implements Serializable {
}
