package com.project.moviegenie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 실패했습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),
    NOT_FOUND_GENRE(HttpStatus.NOT_FOUND, "해당 장르는 없는 장르입니다."),
    NOT_FOUND_PASSWORD(HttpStatus.NOT_FOUND, "패스워드를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
