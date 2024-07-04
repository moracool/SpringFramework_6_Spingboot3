package com.moracool.springboot.curso.error.springboot_error.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.moracool.springboot.curso.error.springboot_error.exceptions.UserNotFoundException;
import com.moracool.springboot.curso.error.springboot_error.models.ErrorDto;

@RestControllerAdvice
public class HandlerExceptionController {


     @ExceptionHandler({ArithmeticException.class})
    public ResponseEntity<ErrorDto> divisionByZero(Exception ex) {

        ErrorDto error = new ErrorDto();
        error.setDate(new Date());
        error.setError("Error division por cero TEST!");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // return ResponseEntity.internalServerError().body(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
    }

    
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> numberFormatException(Exception ex){

        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "Valor numero invalido o incorrecto, no tiene formato de digito.");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDto> notFoundEx(NoHandlerFoundException e) { 
        ErrorDto error = new ErrorDto();
        error.setDate(new Date());
        error.setError("Api rest no encontrado!!!");
        error.setMessage(e.getMessage());

        error.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler({NullPointerException.class, 
            HttpMessageNotWritableException.class,
            UserNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> userNotFoundException(Exception ex){

        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "El usuario o role no existe!");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }
}
