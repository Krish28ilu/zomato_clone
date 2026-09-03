package com.study.zomato_clone.exception;


import com.study.zomato_clone.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDTO handleInvalidRequestException(InvalidRequestException e){
        System.out.println("I am in handler");
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMsg(e.getMessage());
        System.out.println("I am sending response from handler");
        return errorDTO;


    }

    @ExceptionHandler(exception = RestaurantAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorDTO handleRestaurantAlreadyExistException(RestaurantAlreadyExistException e){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMsg(e.getMessage());
        return errorDTO;
    }
}
