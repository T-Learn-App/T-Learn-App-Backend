package projectpractice.tlearnapp.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private String userEmail;

    public UserAlreadyExistsException() {}

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, String userEmail) {
        super(message);
        this.userEmail = userEmail;
    }
}
