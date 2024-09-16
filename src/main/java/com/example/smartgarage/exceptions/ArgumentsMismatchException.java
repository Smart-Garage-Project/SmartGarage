package com.example.smartgarage.exceptions;

public class ArgumentsMismatchException extends RuntimeException {
    public ArgumentsMismatchException(String type, String attribute, String value) {
        super(String.format("%s with %s does not match %s .", type, attribute, value));
    }
}
