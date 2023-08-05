package com.project.moviegenie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String code;
    private T result;
    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }
    public static <T> Response error(String errorCode, T result) {
        return new Response<>(errorCode, result);
    }
}
