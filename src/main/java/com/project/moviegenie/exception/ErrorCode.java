package com.project.moviegenie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
