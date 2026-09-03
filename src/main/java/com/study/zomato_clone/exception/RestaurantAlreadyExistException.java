package com.study.zomato_clone.exception;

public class RestaurantAlreadyExistException extends RuntimeException {
    public RestaurantAlreadyExistException(String msg) {
        super(msg);
    }
}
