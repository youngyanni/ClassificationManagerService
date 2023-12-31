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
    private static class Exception {
        private String message;
        private String dataName;
    }
    @Data
    @AllArgsConstructor
    private static class ExceptionValues{
        private String message;
        private String flag;
        private Object values;
    }
    @ExceptionHandler(ModelNotFoundException.class)
    protected ResponseEntity<Exception> handleNoFoundModelException(ModelNotFoundException model) {
        return new ResponseEntity<>(new Exception("Model with that name was not found", model.getModelName()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateNameError.class)
    protected ResponseEntity<Exception> handleDupicateNameException(DuplicateNameError modelName){
        return new ResponseEntity<>(new Exception("Model with that name already exists",modelName.getModelName()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataProccessingToolsNotFound.class)
    protected ResponseEntity<Exception> handleDataProccessingToolsException(DataProccessingToolsNotFound toolsName){
        return new ResponseEntity<>(new Exception("Data tools with this name already exists",toolsName.getNameAlg()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ValueOutOfRange.class)
    protected ResponseEntity<ExceptionValues> ValueOutOfRangeException(ValueOutOfRange param){
        return new ResponseEntity<>(new ExceptionValues("Value of hyperparameters out of range",param.getFlag(),param.getValue()),HttpStatus.NOT_FOUND);
    }
}
