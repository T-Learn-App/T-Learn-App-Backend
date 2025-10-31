package projectpractice.tlearnapp.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projectpractice.tlearnapp.dto.responses.ApiErrorResponse;
import projectpractice.tlearnapp.exceptions.GetWordException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(GetWordException.class)
    public ResponseEntity<ApiErrorResponse> handleGetWordException(GetWordException e) {
        ApiErrorResponse error = new ApiErrorResponse(
                500,
                e.getClass().getSimpleName(),
                "Get word error"
        );
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(error);
    }
}
