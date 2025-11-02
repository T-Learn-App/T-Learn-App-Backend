package projectpractice.tlearnapp.common.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectpractice.tlearnapp.dto.responses.ApiErrorResponse;
import projectpractice.tlearnapp.exceptions.BusinessException;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrorResponse handleGetWordException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage(), e);

        return new ApiErrorResponse(
                422,
                e.getClass().getSimpleName(),
                "Server can't process the request"
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleDataNotFoundException(DataNotFoundException e) {
        log.error("DataNotFoundException: {}", e.getMessage(), e);

        return new ApiErrorResponse(
                404,
                e.getClass().getSimpleName(),
                "Data not found"
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidRequestException(InvalidRequestException e) {
        log.error("InvalidRequestException: {}", e.getMessage(), e);

        return new ApiErrorResponse(
                400,
                e.getClass().getSimpleName(),
                "Invalid request"
        );
    }
}
