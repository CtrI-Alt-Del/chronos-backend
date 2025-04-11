package br.com.chronos.server.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.chronos.core.auth.domain.exceptions.NotAuthenticatedException;
import br.com.chronos.core.global.domain.exceptions.AppException;
import br.com.chronos.core.global.domain.exceptions.ConflictException;
import br.com.chronos.core.global.domain.exceptions.NotFoundException;
import br.com.chronos.core.global.domain.exceptions.NotPermitException;
import br.com.chronos.core.global.domain.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class ApiExceptionHandler {
  @Data
  @AllArgsConstructor
  private static class ExceptionMessage {
    private String title;
    private String message;
  }

  @ExceptionHandler(NotFoundException.class)
  private ResponseEntity<ExceptionMessage> handleNotFoundException(NotFoundException exception) {
    var message = new ExceptionMessage(exception.getTitle(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }

  @ExceptionHandler(ValidationException.class)
  private ResponseEntity<ExceptionMessage> handleValidationException(ValidationException exception) {
    var message = new ExceptionMessage(exception.getTitle(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
  }

  @ExceptionHandler(AppException.class)
  private ResponseEntity<ExceptionMessage> handleAppException(AppException exception) {
    var message = new ExceptionMessage(exception.getTitle(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }

  @ExceptionHandler(NotAuthenticatedException.class)
  private ResponseEntity<ExceptionMessage> handleNotAuthenticatedException(NotAuthenticatedException exception) {
    var message = new ExceptionMessage(exception.getTitle(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
  }

  @ExceptionHandler(ConflictException.class)
  private ResponseEntity<ExceptionMessage> handleConflictException(ConflictException exception) {
    var message = new ExceptionMessage(exception.getTitle(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }

  @ExceptionHandler(NotPermitException.class)
  private ResponseEntity<ExceptionMessage> handleNotPermitException(NotPermitException exception) {
    var message = new ExceptionMessage(exception.getTitle(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
  }

  @ExceptionHandler(RuntimeException.class)
  private ResponseEntity<ExceptionMessage> handleRunTimeException(RuntimeException exception) {
    var message = new ExceptionMessage("Run time exception", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }
}
