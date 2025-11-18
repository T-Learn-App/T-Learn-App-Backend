package projectpractice.tlearnapp.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectpractice.tlearnapp.controllers.UsersController;
import projectpractice.tlearnapp.controllers.WordsController;
import projectpractice.tlearnapp.dto.responses.ApiErrorResponse;
import projectpractice.tlearnapp.exceptions.BusinessException;
import projectpractice.tlearnapp.exceptions.ConflictException;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrorResponse handleGetWordException(BusinessException e) {
        return new ApiErrorResponse(
                422,
                e.getClass().getSimpleName(),
                "Server can't process the request"
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleDataNotFoundException(DataNotFoundException e) {
        return new ApiErrorResponse(
                404,
                e.getClass().getSimpleName(),
                "Data not found"
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidRequestException(InvalidRequestException e) {
        return new ApiErrorResponse(
                400,
                e.getClass().getSimpleName(),
                "Invalid request"
        );
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleConflictException(ConflictException e) {
        return new ApiErrorResponse(
                409,
                e.getClass().getSimpleName(),
                "Data already exists"
        );
    }
}
