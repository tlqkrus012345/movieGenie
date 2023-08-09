package com.project.moviegenie.exception;

import com.project.moviegenie.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MovieGenieAppException.class)
    public ResponseEntity<?> applicationExceptionHandler(MovieGenieAppException e) {
        log.error("error occurs : {}", e.getErrorMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.error(e.getErrorCode().toString(), e.getErrorMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationExceptionHandler(BindException e) {
        log.error("error occurs : {}", ErrorCode.VALIDATION_ERROR.getMessage());

        List<ApiResponse.ValidationErrorResponse> validationErrorList = new ArrayList<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            ApiResponse.ValidationErrorResponse validationError = ApiResponse.ValidationErrorResponse.of(fieldError);
            validationErrorList.add(validationError);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().resultCode(ErrorCode.VALIDATION_ERROR.toString())
                        .result(null)
                        .validationErrorList(validationErrorList)
                        .build());
    }
}
