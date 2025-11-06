package projectpractice.tlearnapp.common.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectpractice.tlearnapp.controllers.UsersController;
import projectpractice.tlearnapp.dto.responses.ApiErrorResponse;
import projectpractice.tlearnapp.exceptions.UserAlreadyExistsException;

@RestControllerAdvice(assignableTypes = UsersController.class)
@Slf4j
public class UsersAdviceController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleDataAlreadyExistException(UserAlreadyExistsException e) {
        log.error("UserAlreadyExistsException: {}", e.getMessage(), e);

        return new ApiErrorResponse(
                409,
                e.getClass().getSimpleName(),
                "The user already exists"
        );
    }
}
