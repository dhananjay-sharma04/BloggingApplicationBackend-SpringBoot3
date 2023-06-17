package com.blogapp.exceptions;

public class ResourceNotFound extends Exception{
    private final String message;

    public ResourceNotFound(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResourceNotFound{" +
                "message='" + message + '\'' +
                '}';
    }
}