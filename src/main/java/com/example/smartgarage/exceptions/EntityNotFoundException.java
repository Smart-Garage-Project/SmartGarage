package com.example.smartgarage.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public EntityNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }

    public EntityNotFoundException(String type, int id, String value) {
        super(String.format("%s with %d was not found in %s's wish-list.", type, id, value));
    }
}
