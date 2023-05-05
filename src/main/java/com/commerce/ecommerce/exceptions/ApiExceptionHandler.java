package com.commerce.ecommerce.exceptions;

import com.commerce.ecommerce.dto.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.error("{}",exception.getMessage());


        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .data(exception.getMessage())
                .message("please check the field below")
                .statusCode("20")
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }


    @ExceptionHandler(value = {ApiGenericException.class})
    public ResponseEntity<Object> handleApiException(ApiGenericException exception) {
        return ResponseEntity.status(400).body(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleGlobalExceptions(MethodArgumentNotValidException ex) {
        String[] errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .data(errors)
                .message("please check the field below")
                .statusCode("20")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}

