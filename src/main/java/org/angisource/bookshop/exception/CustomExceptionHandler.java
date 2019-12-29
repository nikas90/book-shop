package org.angisource.bookshop.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        HashMap<String, String> mapErr = new HashMap<>();
        mapErr.put("error", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), mapErr, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(NotFoundException ex, WebRequest request) {
        HashMap<String, String> mapErr = new HashMap<>();
        mapErr.put("error", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), mapErr, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HashMap<String, String> mapErr = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            mapErr.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            mapErr.put(error.getObjectName(), error.getDefaultMessage());
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), mapErr, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}