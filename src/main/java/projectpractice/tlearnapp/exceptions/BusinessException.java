package projectpractice.tlearnapp.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
    }
}
