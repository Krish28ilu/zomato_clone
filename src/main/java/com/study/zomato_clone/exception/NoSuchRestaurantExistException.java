package com.study.zomato_clone.exception;

public class NoSuchRestaurantExistException extends RuntimeException {
    public NoSuchRestaurantExistException(String msg) {
        super(msg);
    }
}
