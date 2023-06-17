package com.blogapp.exceptions;

public class ResourceAlreadyExists extends Exception{
    private final String message;

    public ResourceAlreadyExists(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResourceAlreadyExists{" +
                "message='" + message + '\'' +
                '}';
    }
}