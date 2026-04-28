    package com.rachidy.sassgestionstockapp.exceptions;

    import com.rachidy.sassgestionstockapp.exceptions.responses.ErrorResponse;
    import jakarta.persistence.EntityNotFoundException;
    import jakarta.servlet.http.HttpServletRequest;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.HttpStatusCode;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.FieldError;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    import java.net.http.HttpClient;
    import java.util.ArrayList;
    import java.util.List;

    @RestControllerAdvice
    @Slf4j
    public class GlobalHandlerException {

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponse>handelException(
                BusinessException ex,
                HttpServletRequest request
        ){
            final ErrorResponse response= ErrorResponse.builder()
                    .code(ex.getMessage())
                    .path(request.getRequestURI())
                    .build();

            final HttpStatus status=getHttpStatus(ex);

            return ResponseEntity.status(status).body(response);
        }



        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse>handelEntityNotFoundException(
                EntityNotFoundException ex,
                HttpServletRequest httpServletRequest
        ){
            log.error("Entity  not found");
            final ErrorResponse errorResponse = ErrorResponse.builder()
                    .code("NOT_FOUND")
                    .message(ex.getMessage())
                    .path(httpServletRequest.getRequestURI())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        private HttpStatus getHttpStatus(final BusinessException ex) {
            if (ex instanceof DuplicateResourceException){
                return HttpStatus.CONFLICT;
            }
            return HttpStatus.BAD_REQUEST;
        }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse>handelException(
            final MethodArgumentNotValidException ex,
            final HttpServletRequest request
        ){
            log.error("Bad Request : validation request ");

            final List<ErrorResponse.ValidationError> errors=new ArrayList<ErrorResponse.ValidationError>();
            ex.getBindingResult()
                    .getAllErrors()
                    .forEach(error->{
                        final String fieldName= ((FieldError) error).getField();
                        final String  errorCode=error.getDefaultMessage();
                        final String defaultMessage=error.getDefaultMessage(); // todo add trans later

                        errors.add(ErrorResponse.ValidationError.builder()
                                        .filed(fieldName)
                                        .code(errorCode)
                                        .message(defaultMessage)
                                .build());
                    });

            final ErrorResponse errorResponse=ErrorResponse.builder()
                    .path(request.getRequestURI())
                    .validationErrors(errors)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

    }
