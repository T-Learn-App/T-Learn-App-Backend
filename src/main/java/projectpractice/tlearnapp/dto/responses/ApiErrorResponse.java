package projectpractice.tlearnapp.dto.responses;

public record ApiErrorResponse(long statusCode,
                               String exception,
                               String errorMessage) {
}
