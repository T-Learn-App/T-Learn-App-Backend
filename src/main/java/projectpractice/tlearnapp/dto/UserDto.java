package projectpractice.tlearnapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserDto(Long id,
                      @Email @NotBlank String email) implements Serializable {
}
