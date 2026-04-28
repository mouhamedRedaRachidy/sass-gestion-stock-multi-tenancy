package com.rachidy.sassgestionstockapp.exceptions.responses;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final String message;
    private final String code;
    private final String path;
    private final List<ValidationError> validationErrors;

    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class ValidationError{
        private final String filed;
        private final String message;
        private final String code;
    }

}
