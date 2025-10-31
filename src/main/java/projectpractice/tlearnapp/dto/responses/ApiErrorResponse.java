package projectpractice.tlearnapp.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;


public record ApiErrorResponse(@JsonProperty("status") long statusCode,
                               @JsonProperty String errorMessage,
                               @JsonProperty Map<String, String> result) {

}
