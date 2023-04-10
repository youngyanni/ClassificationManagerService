package ru.mtuci.is_c.ml.classification_manager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    @Data
    @AllArgsConstructor
    private static class NotFoundModelException {
        private String message;
        private String modelName;

    }

    @ExceptionHandler(ModelNotFoundException.class)
    protected ResponseEntity<NotFoundModelException> handleNoFoundModelException(ModelNotFoundException model) {
        return new ResponseEntity<>(new NotFoundModelException("Model with that name was not found", model.getModelName()), HttpStatus.NOT_FOUND);
    }
}
