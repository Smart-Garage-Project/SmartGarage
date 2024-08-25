package com.example.smartgarage.exceptions;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }

    public EntityDuplicateException(String type) {
        super(String.format("%s already exists.", type));
    }
}
