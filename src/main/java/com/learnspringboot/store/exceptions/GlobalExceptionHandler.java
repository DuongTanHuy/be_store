package com.learnspringboot.store.exceptions;

import com.learnspringboot.store.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ========== Business Exceptions ==========

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExists(AlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(e.getMessage(), null));
    }

    // ========== Security Exceptions ==========

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse("You don't have permission to access this resource", null));
    }

    @ExceptionHandler({
            AuthenticationException.class,
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ApiResponse> handleAuthentication(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Unauthorized: " + e.getMessage(), null));
    }

    // ========== Validation ==========

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest()
                .body(new ApiResponse("Validation failed", errors));
    }

    // ========== Database ==========

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse> handleSQLException(SQLException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Database error", e.getMessage()));
    }

    // ========== Fallback ==========

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Internal server error", e.getMessage()));
    }
}