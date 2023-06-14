package com.buildingblocks.base.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Set;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        CustomErrorDetails customErrorDetails =
                new CustomErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        CustomErrorDetails customErrorDetails =
                new CustomErrorDetails(new Date(),
                        "From HttpRequestMethodNotSupported - Method Not Allowed",
                        ex.getMessage());
        return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // UserNameNotFoundException
    @ExceptionHandler(UserNameNotFoundException.class)
    public final ResponseEntity<Object> handleUserNameNotFoundException(UserNameNotFoundException ex, WebRequest request) {
        CustomErrorDetails customErrorDetails =
                new CustomErrorDetails(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    // UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        CustomErrorDetails customErrorDetails =
                new CustomErrorDetails(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    // OrderNotFoundException
    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        CustomErrorDetails customErrorDetails =
                new CustomErrorDetails(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    // ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           WebRequest request){
        CustomErrorDetails customErrorDetails =
                new CustomErrorDetails(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

}
