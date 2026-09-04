package com.study.zomato_clone.exception;

public class NoSuchUserExistException extends RuntimeException {
    public NoSuchUserExistException(String msg) {
        super(msg);
    }
}
