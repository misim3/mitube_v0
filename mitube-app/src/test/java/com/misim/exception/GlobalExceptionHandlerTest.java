package com.misim.exception;

import static com.misim.exception.MitubeErrorCode.INVALID_NICKNAME;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleMitubeException_shouldReturnExpectedResponse() {
        MitubeException exception = new MitubeException(INVALID_NICKNAME);

        CommonResponse<?> response = globalExceptionHandler.handleMitubeException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
        assertEquals(INVALID_NICKNAME.getCode(), response.getBody());
        assertEquals(INVALID_NICKNAME.getMessage(), response.getMessage());
    }

    @Test
    void handleEntityNotFoundException_shouldReturnExpectedResponse() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        CommonResponse<?> response = globalExceptionHandler.handleEntityNotFoundException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
        assertEquals(88888, response.getBody());
        assertEquals("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.", response.getMessage());
    }

    @Test
    void handleUnknownException_shouldReturnExpectedResponse() {
        Exception exception = new Exception("Unexpected error");

        CommonResponse<?> response = globalExceptionHandler.handleUnknownException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getHttpStatus());
        assertEquals(MitubeErrorCode.UNKNOWN_EXCEPTION.getCode(), response.getBody());
        assertEquals(MitubeErrorCode.UNKNOWN_EXCEPTION.getMessage(), response.getMessage());
    }
}
