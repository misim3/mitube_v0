package com.misim.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MitubeException.class)
    public CommonResponse<?> handleMitubeException(MitubeException e) {

        log.error("Handler MitubeException: {}", e.getMessage(), e);

        return new CommonResponse<>(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage(), e.getErrorCode().getCode());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public CommonResponse<?> handleEntityNotFoundException(EntityNotFoundException e) {

        log.error("Handler EntityNotFoundException: {}", e.getMessage(), e);

        return new CommonResponse<>(HttpStatus.NOT_FOUND, "해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.", 88888);
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleUnknownException(Exception e) {

        log.error("Handler UnknownException: {}", e.getMessage(), e);

        return new CommonResponse<>(MitubeErrorCode.UNKNOWN_EXCEPTION.getHttpStatus(), MitubeErrorCode.UNKNOWN_EXCEPTION.getMessage(), MitubeErrorCode.UNKNOWN_EXCEPTION.getCode());
    }
}
