package com.misim.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MitubeException.class)
    public ResponseEntity<CommonResponse<?>> handleMitubeException(MitubeException e) {

        log.error("Handler MitubeException: {}", e.getMessage(), e);

        CommonResponse<?> commonResponse = new CommonResponse<>(e.getErrorCode().getCode(),
            e.getErrorCode().getMessage(), null);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(commonResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handleEntityNotFoundException(EntityNotFoundException e) {

        log.error("Handler EntityNotFoundException: {}", e.getMessage(), e);

        CommonResponse<?> commonResponse = new CommonResponse<>(88888, "해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleUnknownException(Exception e) {

        log.error("Handler UnknownException: {}", e.getMessage(), e);

        CommonResponse<?> commonResponse = new CommonResponse<>(
            MitubeErrorCode.UNKNOWN_EXCEPTION.getCode(),
            MitubeErrorCode.UNKNOWN_EXCEPTION.getMessage(), null);
        return ResponseEntity.status(MitubeErrorCode.UNKNOWN_EXCEPTION.getHttpStatus())
            .body(commonResponse);
    }
}
