package com.project.moviegenie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieGenieAppException extends RuntimeException{
    
    private ErrorCode errorCode;
    private String errorMessage;

    public MovieGenieAppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        if (errorMessage == null) return errorCode.getMessage();
        return String.format("%s, %s", errorCode.getMessage(), errorMessage);
    }
}
