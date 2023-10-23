package com.lovely4k.backend.common;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleValidation(DateTimeParseException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetailCreator.create(e, request, HttpStatus.BAD_REQUEST);

        return ApiResponse.fail(HttpStatus.BAD_REQUEST, problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetailCreator.createValidationDetails(e, request, HttpStatus.BAD_REQUEST);

        return ApiResponse.fail(HttpStatus.BAD_REQUEST, problemDetail);
    }
}
