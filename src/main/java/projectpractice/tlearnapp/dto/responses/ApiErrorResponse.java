package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ApiErrorResponse(@JsonProperty("status") long statusCode,
                               @JsonProperty String exception,
                               @JsonProperty String errorMessage) {
}
