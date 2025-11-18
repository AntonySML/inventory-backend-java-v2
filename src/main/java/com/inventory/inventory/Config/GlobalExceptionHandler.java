package com.inventory.inventory.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

import com.inventory.inventory.Commons.dto.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex) {
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500 por defecto
        String message = ex.getMessage();

        // Lógica simple para cambiar el status según el mensaje 
        // (En un proyecto real, usarías Excepciones personalizadas como ResourceNotFoundException)
        if (message.contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND; // 404
        } else if (message.contains("No tienes permiso")) {
            status = HttpStatus.FORBIDDEN; // 403
        } else if (message.contains("ya se encuentra registrado")) {
            status = HttpStatus.BAD_REQUEST; // 400
        }

        ErrorDTO error = ErrorDTO.builder()
            .message(message)
            .code(String.valueOf(status.value()))
            .build();

        return new ResponseEntity<>(error, status);
    }

    // Maneja errores de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityException(DataIntegrityViolationException ex) {
        
        String message = "Error de integridad en la base de datos. Revise que los datos requeridos estén completos y no duplicados.";

        ErrorDTO error = ErrorDTO.builder()
            .message(message)
            .code("409") // Conflict
            .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
