package com.example.login.utils;

import com.example.login.model.Usuario;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseBuilder {
    public static Map<String, Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error",message);
        errorResponse.put("status",status.value());
        errorResponse.put("timestamp",System.currentTimeMillis());
        return errorResponse;
    }
}
