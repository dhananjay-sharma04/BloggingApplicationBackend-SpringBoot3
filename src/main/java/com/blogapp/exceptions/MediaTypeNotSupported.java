package com.blogapp.exceptions;

public class MediaTypeNotSupported extends Exception{
    private final String message;

    public MediaTypeNotSupported(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "MediaTypeNotSupported{" +
                "message='" + message + '\'' +
                '}';
    }
}