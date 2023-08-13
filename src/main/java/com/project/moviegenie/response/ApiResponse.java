package com.project.moviegenie.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

    private String resultCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T result;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationErrorResponse> validationErrorList;
    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>("SUCCESS", result,null);
    }
    public static ApiResponse<String> error(String resultCode, String message) {
        return new ApiResponse<>(resultCode, message,null);
    }
    public static ApiResponse<?> success() {
        return new ApiResponse<>("SUCCESS",null , null);
    }
    /**
     *  에러 목록의 각 항목을 나타내는 내부 클래스
     */
    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationErrorResponse {
        private final String field;
        private final String message;

        public static ValidationErrorResponse of(final FieldError fieldError) {
            return ValidationErrorResponse.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
