package com.waal.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.message = message;
        response.data = data;
        return response;
    }

    public static ApiResponse<Void> ok(String message) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.success = true;
        response.message = message;
        return response;
    }

    public static ApiResponse<Void> fail(String message) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        return response;
    }
}
