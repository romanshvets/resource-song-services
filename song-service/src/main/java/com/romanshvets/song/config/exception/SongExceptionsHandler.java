package com.romanshvets.song.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class SongExceptionsHandler {

    @ExceptionHandler(SongValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(SongValidationException e) {
        Map<String, Object> body = Map.of(
                "errorCode", Integer.toString(e.getErrorCode()),
                "errorMessage", e.getErrorMessage(),
                "details", e.getDetails()
        );

        return ResponseEntity
                .status(e.getErrorCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(SongGenericException.class)
    public ResponseEntity<Map<String, Object>> handleSimpleException(SongGenericException e) {
        Map<String, Object> body = Map.of(
                "errorCode", Integer.toString(e.getErrorCode()),
                "errorMessage", e.getErrorMessage()
        );

        return ResponseEntity
                .status(e.getErrorCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred on the server");
    }
}
