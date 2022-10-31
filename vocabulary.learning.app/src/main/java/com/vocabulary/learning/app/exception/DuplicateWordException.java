package com.vocabulary.learning.app.exception;

public class DuplicateWordException extends RuntimeException{
    public DuplicateWordException(String message){
        super(message);
    }
}
