package com.misim.exception;

import static com.misim.exception.MitubeErrorCode.INVALID_NICKNAME;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleMitubeException_shouldReturnExpectedResponse() {
        MitubeException exception = new MitubeException(INVALID_NICKNAME);

        ResponseEntity<CommonResponse<?>> response = globalExceptionHandler.handleMitubeException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(INVALID_NICKNAME.getCode(), Objects.requireNonNull(
            response.getBody()).getCode());
        assertEquals(INVALID_NICKNAME.getMessage(), response.getBody().getMessage());
    }

    @Test
    void handleEntityNotFoundException_shouldReturnExpectedResponse() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        ResponseEntity<CommonResponse<?>> response = globalExceptionHandler.handleEntityNotFoundException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(88888, Objects.requireNonNull(response.getBody()).getCode());
        assertEquals("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.", response.getBody().getMessage());
    }

    @Test
    void handleUnknownException_shouldReturnExpectedResponse() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<CommonResponse<?>> response = globalExceptionHandler.handleUnknownException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MitubeErrorCode.UNKNOWN_EXCEPTION.getCode(), Objects.requireNonNull(
            response.getBody()).getCode());
        assertEquals(MitubeErrorCode.UNKNOWN_EXCEPTION.getMessage(), response.getBody().getMessage());
    }
}
