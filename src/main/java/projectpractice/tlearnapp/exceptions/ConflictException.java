package projectpractice.tlearnapp.exceptions;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    public ConflictException() {}

    public ConflictException(String message) {
        super(message);
    }
}
